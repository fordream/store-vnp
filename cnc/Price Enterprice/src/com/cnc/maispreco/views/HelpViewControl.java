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
//		String content = "Dicas de Procura do Mais Preço:\n"
//				+ "Aqui vão algumas dicas para que você encontre o produto que procura mais facilmente.\n"
//
//				+ "\n1. Descrição"
//				+ "\nAo procurar por um produto ou serviço no Mais Preço, seja o mais genérico possível. Utilize apenas uma ou duas palavras, evite sentenças longas ou excesso de itens na descrição. Por exemplo: \"Paracetamol\" ao invés de \"Paracetamol Gotas Genérico 15 ml\". Você poderá especificar melhor os detalhes quando chegar na página de resultados, utilizando nosso refino.\n"
//
//				// +"\n2. Digitação\n"
//				// +
//				// "É muito comum ocorrer um erro na hora de digitar o produto, esta é inclusive a maior causa de erros nos resultados do Mais Preço. Para minimizar esse problema, nossa barra de pesquisa apresenta algumas sugestões enquanto você digita o nome do seu medicamento. Se tiver dúvida, digite apenas parte da palavra, por exemplo: \"Parac\" e deixe que a nossa barra de busca sugira os produtos que mais se aproximam desse termo.\n\n"
//				//
//				// + "3. Utilize o nosso filtro\n"
//				// +
//				// "Quando você chega a uma página de resultados, encontra o filtro na parte esquerda de sua tela. Ele foi criado para refinar seus resultados. Por exemplo, procurando por \"Paracetamol Gotas Genérico 15 ml\", você digitou \"Paracetamol\" e chegou na página de resultados. Agora você utiliza o filtro, escolhendo a apresentação \"15 ml\" e obterá somente a resposta desejada. Com ele você também pode refinar por marca, nome comercial, além de manter ou retirar palavras de seus resultados.\n\n"
//
//				+ "\n2. Não utilize \"e\" ou \"ou\"\n"
//				+ "Nosso sistema de busca não reconhece \"e\" ou \"ou\" como termos de busca, e elas podem atrapalhar o sistema."
//
//				+ "\n\n3. Evite utilizar pontuação eou símbolos\n"
//				+ "Procure utilizar apenas palavras em sua busca. Caracteres como (e.g.,?, !, &, @ or $) normalmente não trazem resultados satisfatórios. Quando se trata de marcas como \"Jonhson & Jonhson\", utilize \"Jonhson Jonhson\". Não utilize também símbolos relativos a moedas.\n\n"
//
//				+ "4. Não digite endereços da internet\n"
//				+ "Nunca busque por \"www.nomedamarca.com.br\". Procure sempre palavras relacionadas a produtos e ou categorias, utilizando apenas a marca e o substantivo que faz referência ao nome de um produto."
//				+ "\n\n5. Não use abreviaturas ou palavras digitadas juntas"
//				+ "\nSempre digite o nome mais comum de um produto. Por exemplo: Ao invés de \"paracetamolgotas\" opte por Paracetamol Gotas.";
//		return content;
//	}
}