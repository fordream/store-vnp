package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpViewControl extends LinearLayout {
	private TextView tVConentAboutHelp;
	Context context;

	public HelpViewControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config();
	}

	public HelpViewControl(Context context) {
		super(context);
		this.context = context;
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.help1, this);
		tVConentAboutHelp = (TextView) findViewById(R.id.tVConentAboutHelp);
		//tVConentAboutHelp.setText(createText());
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				TrackerGoogle.homeTracker(context, "/faleconosco");
				return null;
			}
		}.execute("");
	}

	public TextView gettVConentAboutHelp() {
		return tVConentAboutHelp;
	}

	public void settVConentAboutHelp(TextView tVConentAboutHelp) {
		this.tVConentAboutHelp = tVConentAboutHelp;
	}

//	private String createText() {
//		String content = "Dicas de Procura do Mais Pre�o:\n"
//				+ "Aqui v�o algumas dicas para que voc� encontre o produto que procura mais facilmente.\n"
//
//				+ "\n1. Descri��o"
//				+ "\nAo procurar por um produto ou servi�o no Mais Pre�o, seja o mais gen�rico poss�vel. Utilize apenas uma ou duas palavras, evite senten�as longas ou excesso de itens na descri��o. Por exemplo: \"Paracetamol\" ao inv�s de \"Paracetamol Gotas Gen�rico 15 ml\". Voc� poder� especificar melhor os detalhes quando chegar na p�gina de resultados, utilizando nosso refino.\n"
//
//				// +"\n2. Digita��o\n"
//				// +
//				// "� muito comum ocorrer um erro na hora de digitar o produto, esta � inclusive a maior causa de erros nos resultados do Mais Pre�o. Para minimizar esse problema, nossa barra de pesquisa apresenta algumas sugest�es enquanto voc� digita o nome do seu medicamento. Se tiver d�vida, digite apenas parte da palavra, por exemplo: \"Parac\" e deixe que a nossa barra de busca sugira os produtos que mais se aproximam desse termo.\n\n"
//				//
//				// + "3. Utilize o nosso filtro\n"
//				// +
//				// "Quando voc� chega a uma p�gina de resultados, encontra o filtro na parte esquerda de sua tela. Ele foi criado para refinar seus resultados. Por exemplo, procurando por \"Paracetamol Gotas Gen�rico 15 ml\", voc� digitou \"Paracetamol\" e chegou na p�gina de resultados. Agora voc� utiliza o filtro, escolhendo a apresenta��o \"15 ml\" e obter� somente a resposta desejada. Com ele voc� tamb�m pode refinar por marca, nome comercial, al�m de manter ou retirar palavras de seus resultados.\n\n"
//
//				+ "\n2. N�o utilize \"e\" ou \"ou\"\n"
//				+ "Nosso sistema de busca n�o reconhece \"e\" ou \"ou\" como termos de busca, e elas podem atrapalhar o sistema."
//
//				+ "\n\n3. Evite utilizar pontua��o eou s�mbolos\n"
//				+ "Procure utilizar apenas palavras em sua busca. Caracteres como (e.g.,?, !, &, @ or $) normalmente n�o trazem resultados satisfat�rios. Quando se trata de marcas como \"Jonhson & Jonhson\", utilize \"Jonhson Jonhson\". N�o utilize tamb�m s�mbolos relativos a moedas.\n\n"
//
//				+ "4. N�o digite endere�os da internet\n"
//				+ "Nunca busque por \"www.nomedamarca.com.br\". Procure sempre palavras relacionadas a produtos e ou categorias, utilizando apenas a marca e o substantivo que faz refer�ncia ao nome de um produto."
//				+ "\n\n5. N�o use abreviaturas ou palavras digitadas juntas"
//				+ "\nSempre digite o nome mais comum de um produto. Por exemplo: Ao inv�s de \"paracetamolgotas\" opte por Paracetamol Gotas.";
//		return content;
//	}
}