package com.vnp.shortfirmfestival_rework.adapter;

import java.util.StringTokenizer;

import com.vnp.core.common.ImageLoaderUtils;
import com.vnp.shortfirmfestival_rework.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppsAdapter extends BaseAdapter {
	public static final String LIST_APPS[] = {
			"Short Films Express_http://www.shortshorts.org/app/images/icon/express-icon.jpg_ショートショート映画祭の正式なデジタルブック_Toshiko goes to Seoul to see her idol. Here, she came into conflict with Park, an unsocial driver._0_https://play.google.com/store/apps/details?id=jp.shortshorts.official#?t=W251bGwsMSw",
			"Super Star_http://www.shortshorts.org/app/images/superstar/superstar-top-icon.png_韓流スターに会うため、ソウルにやってきた敏子は、ひょんなことから無愛想な運転手のパクと行動を共にすることに・・・_3 college students are close friends. One night, they tries to test their bravery by recording thrilling story on cassette_0_https://play.google.com/store/apps/details?id=jp.shortshorts.star&feature=search_res",
			"23話目_http://www.shortshorts.org/app/images/23/23-top-icon.png_仲のいい大学生の3人は、ある夜『カセットテープに怪談話を吹き込む』という肝試しを始める。_Horita (played by Katamme Jun) is a bicycle delivery man. He receives a request to deliver “a forgotten thing” to Tajima(played by Ishihara Yoshizumi), MC of “the secretshow”, a television program. Horita rushes to the TV station. But in his way there, a lot of troubles come to him._350_https://play.google.com/store/apps/details?id=jp.shortshorts.the23rd&feature=search_result",
			"THE SECRET SHOW_http://www.shortshorts.org/app/images/secret/secret-top-icon.png_自転車便の堀田(要潤)への依頼は、TV番組「ザ・シークレットショウ」司会者の田島(石原良純)へ '忘れ物'を届けること。TV局目指して疾走する、堀田。だがそこには数々の困難が・・・_Love in 10 minutes… (Ooguno maki – played by actress Kinoshita Yukina)A widowed politician... Another day, Gal, who calls herself his wife’s friend appears_350_https://play.google.com/store/apps/details?id=jp.shortshorts.secret#?t=W251bGwsMSwxL",
			"ゆっきーな_http://www.shortshorts.org/app/images/icon/yukkina-top-icon.png_この10分「愛」がある…大黒摩季×女優・木下優樹菜 妻を亡くした政治家。ある日、妻の友達と名乗るギャルが現れ…_A life that is all peaceful is not always welcomed. This is a love story that follows the courageous standing up of a young girl after her failed love. Sakukawa Aimi plays a character with lots of personality, often being in half laughing and half crying situations. Neko Hiroshi plays her lover._350_https://play.google.com/store/apps/details?id=jp.shortshorts.yukkina&feature=more_fr",
			"MISTER ROCOCO_http://www.shortshorts.org/app/images/icon/rococo-top-icon.png_可愛いだけの人生なんてもうまっぴら!!失恋した少女の華麗で壮絶な復活劇を描いたラブコメディ。笑って泣ける異色のヒロインを佐津川愛美が熱演。恋人役に猫ひろし(!?)_International short film festival with the cooperation of Superfly America Academy Award._350_https://play.google.com/store/apps/details?id=jp.shortshorts.rococo",
			"映画 『皆既日食の..._http://www.shortshorts.org/app/images/icon/eclipse-top-icon.png_Superflyと米国アカデミー賞公認アジア最大級の国際短編映画祭がコラボレーション!_Popular short film application is now available on iPad !! As an Academy Awards® accredited film festival, the Short Shorts Film Festival staff proudly presents 6 Spotlight Award Winning short films. Bonus: Special trailers._350_https://play.google.com/store/apps/details?id=jp.shortshorts.eclipse" };

	public AppsAdapter() {
	}

	@Override
	public int getCount() {
		return LIST_APPS.length;
	}

	@Override
	public Object getItem(int position) {
		String string = LIST_APPS[position];
		return string;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.app_item, null);
		}

		ImageView app_img = (ImageView) convertView.findViewById(R.id.app_img);
		TextView app_name = (TextView) convertView.findViewById(R.id.app_name);
		TextView app_description = (TextView) convertView.findViewById(R.id.app_description);
		String data = getItem(position).toString();
		// name_icon_description_description1_price_linkGoogle
		StringTokenizer stringTokenizer = new StringTokenizer(data, "_");

		String name = stringTokenizer.nextToken();
		String icon = stringTokenizer.nextToken();
		String description = stringTokenizer.nextToken();
		String description1 = stringTokenizer.nextToken();
		String price = stringTokenizer.nextToken();
		String linkGoogle = stringTokenizer.nextToken();

		ImageLoaderUtils.getInstance(parent.getContext()).DisplayImage(icon, app_img);
		app_name.setText(name);
		app_description.setText(description1);

		return convertView;
	}

}
