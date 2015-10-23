/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gm.contentprovidertest;

import com.google.android.gm.contentprovider.GmailContract;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

public class Main extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        LabelListFragment.ItemClickedListener {
    static final String TAG = "TestApp";
    LabelListFragment mFragment = null;
    SimpleCursorAdapter mAdapter;

    private static final String ACCOUNT_TYPE_GOOGLE = "com.google";
    private static final String[] FEATURES_MAIL = {"service_mail"};

    static final String[] COLUMNS_TO_SHOW = new String[] {
            GmailContract.Labels.NAME,
            GmailContract.Labels.NUM_CONVERSATIONS,
            GmailContract.Labels.NUM_UNREAD_CONVERSATIONS };

    static final int[] LAYOUT_ITEMS = new int[] {
            R.id.name_entry,
            R.id.number_entry,
            R.id.unread_count_number_entry };

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.main);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        // There is only one fragment
        mFragment = (LabelListFragment)fragment;
        mFragment.setItemClickedListener(this);

        mAdapter = new SimpleCursorAdapter(this, R.layout.label_list_item, null,
                COLUMNS_TO_SHOW, LAYOUT_ITEMS);
        mFragment.setListAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Get the account list, and pick the first one
        AccountManager.get(this).getAccountsByTypeAndFeatures(ACCOUNT_TYPE_GOOGLE, FEATURES_MAIL,
                new AccountManagerCallback<Account[]>() {
                    @Override
                    public void run(AccountManagerFuture<Account[]> future) {
                        Account[] accounts = null;
                        try {
                            accounts = future.getResult();
                        } catch (OperationCanceledException oce) {
                            Log.e(TAG, "Got OperationCanceledException", oce);
                        } catch (IOException ioe) {
                            Log.e(TAG, "Got OperationCanceledException", ioe);
                        } catch (AuthenticatorException ae) {
                            Log.e(TAG, "Got OperationCanceledException", ae);
                        }
                        onAccountResults(accounts);
                    }
                }, null /* handler */);
    }

    private void onAccountResults(Account[] accounts) {
        Log.i("TestApp", "received accounts: " + Arrays.toString(accounts));
        if (accounts != null && accounts.length > 0) {
            // Pick the first one, and display a list of labels
            final String account = accounts[0].name;
            Log.i(TAG, "Starting loader for labels of account: " + account);
            final Bundle args = new Bundle();
            args.putString("account", account);
            getSupportLoaderManager().restartLoader(0, args, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String account = args.getString("account");
        final Uri labelsUri = GmailContract.Labels.getLabelsUri(account);
        return new CursorLoader(this, labelsUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            Log.i(TAG, "Received cursor with # rows: " + data.getCount());
        }
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClicked(int position) {
        // Get the cursor from the adapter
        final Cursor cursor = mAdapter.getCursor();

        cursor.moveToPosition(position);

        // get the uri
        final Uri labelUri = Uri.parse(
                cursor.getString(cursor.getColumnIndex(GmailContract.Labels.URI)));

        Log.i(TAG, "got label uri: " + labelUri);
        final Intent intent = new Intent(this, LabelDetailsActivity.class);
        intent.putExtra(LabelDetailsActivity.LABEL_URI_EXTRA, labelUri);
        startActivity(intent);
    }

}
