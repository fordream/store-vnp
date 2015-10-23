package com.icts.shortfilmfestival.inf;

import com.icts.shortfilmfestivalJa.R;

public interface ISettings {
	
	/**
	 * Url for get news
	 */
	public static final String NEWS_URL_PREFIX = "http://www.shortshorts.org/api/list.php";
	public static final String NEWS_DETAIL_URL_PREFIX = "http://www.shortshorts.org/api/detail.php?type=";
	public static final String YOUTUBE_URL = "http://49.212.161.19/apissff/api/ytvod.php";
	public static final String USTREAM_URL = "http://49.212.161.19/apissff/api/ustream.php";
	public static final String PHOTOS_URL = "http://49.212.161.19/apissff/api/photos.php";
	//public static final String PHOTOS_URL = "http://web.icts.vn/shorts/api/photos.php";
	public static final String SCHEDULE_URL = "http://www.shortshorts.org/api/schedule.php";
	public static final String GET_NUMBER_LIKE_TWITER_URL = "http://urls.api.twitter.com/1/urls/count.json?url=";
	public static final String GET_NUMBER_LIKE_FACEBOOK_URL = "https://graph.facebook.com/";
	public static final String FESTIVAL_TICKET_URL = "https://social.co.jp/gw/sgwp2/l/s48/";
	
	public static final String BIT_FLY_URL = "http://49.212.161.19/apissff/venues.php";
	
	// For EN
	public static final String FESTIVAL_REPORT_EN = "https://www.facebook.com/pages/Short-Shorts-Film-Festival-Asia-for-Worldwide-/304857699586630?sk=notes";
	public static final String FESTIVAL_REPORT_JA = "https://www.facebook.com/shortshortsfilmfestivalasia/notes"; 
	public static final String YOUTUBE_LINK_TOP_NEWS_EN = "http://49.212.161.19/apissff/api/video.php?lang=en";
	public static final String YOUTUBE_LINK_TOP_NEWS_JA = "http://49.212.161.19/apissff/api/video.php?lang=ja";
	
	//For Ja
	//public static final String FESTIVAL_REPORT = "http://www.facebook.com/shortshortsfilmfestivalasia/notes";
	public static final String LANG_ENGLISH = "en";
	public static final String LANG_JP = "ja";
	public static final int TYPE_YOUTUBE = 0;
	public static final int TYPE_USTREAM = 1;
	public static final String LANG_JP_FONT = "ja_JP";
	public static final String LANGUAGE = "ja";
	
	// Data For Apps
	public static final String LIST_APPS[] = 
	{
		"Short Films Express_http://www.shortshorts.org/app/images/icon/express-icon.jpg_ショートショート映画祭の正式なデジタルブック_Toshiko goes to Seoul to see her idol. Here, she came into conflict with Park, an unsocial driver._0_https://play.google.com/store/apps/details?id=jp.shortshorts.official#?t=W251bGwsMSw",
		"Super Star_http://www.shortshorts.org/app/images/superstar/superstar-top-icon.png_韓流スターに会うため、ソウルにやってきた敏子は、ひょんなことから無愛想な運転手のパクと行動を共にすることに・・・_3 college students are close friends. One night, they tries to test their bravery by recording thrilling story on cassette_0_https://play.google.com/store/apps/details?id=jp.shortshorts.star&feature=search_res",
		"23話目_http://www.shortshorts.org/app/images/23/23-top-icon.png_仲のいい大学生の3人は、ある夜『カセットテープに怪談話を吹き込む』という肝試しを始める。_Horita (played by Katamme Jun) is a bicycle delivery man. He receives a request to deliver “a forgotten thing” to Tajima(played by Ishihara Yoshizumi), MC of “the secretshow”, a television program. Horita rushes to the TV station. But in his way there, a lot of troubles come to him._350_https://play.google.com/store/apps/details?id=jp.shortshorts.the23rd&feature=search_result",
		"THE SECRET SHOW_http://www.shortshorts.org/app/images/secret/secret-top-icon.png_自転車便の堀田(要潤)への依頼は、TV番組「ザ・シークレットショウ」司会者の田島(石原良純)へ '忘れ物'を届けること。TV局目指して疾走する、堀田。だがそこには数々の困難が・・・_Love in 10 minutes… (Ooguno maki – played by actress Kinoshita Yukina)A widowed politician... Another day, Gal, who calls herself his wife’s friend appears_350_https://play.google.com/store/apps/details?id=jp.shortshorts.secret#?t=W251bGwsMSwxL",
		"ゆっきーな_http://www.shortshorts.org/app/images/icon/yukkina-top-icon.png_この10分「愛」がある…大黒摩季×女優・木下優樹菜 妻を亡くした政治家。ある日、妻の友達と名乗るギャルが現れ…_A life that is all peaceful is not always welcomed. This is a love story that follows the courageous standing up of a young girl after her failed love. Sakukawa Aimi plays a character with lots of personality, often being in half laughing and half crying situations. Neko Hiroshi plays her lover._350_https://play.google.com/store/apps/details?id=jp.shortshorts.yukkina&feature=more_fr",
		"MISTER ROCOCO_http://www.shortshorts.org/app/images/icon/rococo-top-icon.png_可愛いだけの人生なんてもうまっぴら!!失恋した少女の華麗で壮絶な復活劇を描いたラブコメディ。笑って泣ける異色のヒロインを佐津川愛美が熱演。恋人役に猫ひろし(!?)_International short film festival with the cooperation of Superfly America Academy Award._350_https://play.google.com/store/apps/details?id=jp.shortshorts.rococo",
		"映画 『皆既日食の..._http://www.shortshorts.org/app/images/icon/eclipse-top-icon.png_Superflyと米国アカデミー賞公認アジア最大級の国際短編映画祭がコラボレーション!_Popular short film application is now available on iPad !! As an Academy Awards® accredited film festival, the Short Shorts Film Festival staff proudly presents 6 Spotlight Award Winning short films. Bonus: Special trailers._350_https://play.google.com/store/apps/details?id=jp.shortshorts.eclipse"
	};
	
	public static final int LIST_LOGO_APP[]=
	{
		R.drawable.express,
		R.drawable.world,
		R.drawable.top_app,
		R.drawable.secret,
		R.drawable.yukkina,
		R.drawable.rococo,
		R.drawable.eclipse
		
	};
	
	public static final int LIST_SLIDE[]=
	{
		R.drawable.slide_1,
		R.drawable.slide_2,
		R.drawable.slide_3,
		R.drawable.slide_4,
		R.drawable.slide_5
	};
	
	public static final String LIST_LINK_SLIDE[]=
	{
		"http://www.shortshorts.org/2012/ja/list.php?cid=9",
		"http://www.brillia-sst.jp/",
		"http://www.shortshorts.org/2012/index.php",
		"http://www.shortshorts.org/2012/ja/list.php?cid=18",
		"http://ckantan.jp/dm/mob/dm_comfirm.jsp?cmcd=4100011562"
	};
	
	
}
