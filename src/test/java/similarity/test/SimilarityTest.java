package similarity.test;

import org.ansj.splitWord.analysis.NlpAnalysis;
import org.junit.Test;

import com.timerchina.similarity.EditDistance;
import com.timerchina.similarity.Jaccard;
import com.timerchina.similarity.JaccardImprove;

public class SimilarityTest {
	
	@Test
	public void testEditDis(){
//		String strB = "在庞大的汽车后市场领域，窗膜作为“汽车美容装潢”的一部分，更多的笼罩在众多汽车品牌的光环中，被作为“原厂贴膜”在4S店中出售给消费者。然而，所谓的“原厂贴膜”并非由整车企业来生产制造，而是由其背后的配套供应商提供。康得新，作为一家全产业链的生产制造商，其窗膜一直以来被作为“原厂贴膜”出售给广大消费者。如今，康得新窗膜正通过多种方式与途径试图走到消费者面前。近日，盖世汽车受邀走进了KDX在张家港的工厂——康得新光电材料有限公司。通过实地探访，KDX为我们展开了一个别样的“膜法”世界。全产业链的生产模式，先进工艺业内领先据工作人员介绍，康得新在张家港建造的一期工厂投资45亿人民币，占地面积574亩，用于建设年产2万亿平方米的光学薄膜；二期工厂已经开建，占地490亩，投资120亿人民币，计划2018年完工，用于1亿平方米先进高分子膜材料项目的生产，以及1亿片裸眼3D模组项目。在光学薄膜生产制造方面，KDX引进国外10余条成熟生产线，还拥有价值超2亿元的全球唯一一台双腔12靶的磁控溅射设备，国际化标准建造的厂房，完备的配套设施以及资源优势先进的系统都堪称全亚洲一流的窗膜工厂。除此之外，KDX还通过产业延伸打造裸眼3d、互联网智能应用的板块，碳纤维延伸打造了新能源电动车轻量化生态平台的板块，目前形成了新材料、智能显示、互联网智能应用平台、新能源汽车产业链四大产业板块，这四大板块是在生态体系里相互依存、相互共融和相互发展。深入洞察消费者需求，产品更具差异化和个性化目前，随着汽车行业的快速发展，中国已经成为全球最大的汽车市场，这也为汽车膜的生产与装配提供了巨大的发展空间。据统计，在美国，汽车膜的装配率只有5%到7%，而在中国却达到了85%。虽然早在2001年，康得新（KDX）复合材料集团股份有限公司就已经成立，但直到2015年，KDX窗膜才推向市场，如今，不到两年的时间，KDX窗膜的市场占有率达到了20%，其直接客户覆盖了国内大部分主流整车企业。那么，康得新窗膜有哪些产品优势呢？从生产环节来讲，主要包括以下几个方面- 全产业链的生产工艺- 原色PET的使用- 最先进的多层贵金属磁控溅射生产技术及设备- 纳米级的生产技术，- 最新专利的纳米隔热材料及技术的应用以上工艺和技术的应用使得康得新窗膜产品始终保持在研发、生产、技术等方面的领先水平，，同国际品牌和进口产品品质接轨。此外，在目前消费者对产品需求越来越细化的情况下，KDX从客户的需求出发，推出一系列差异化和个性化产品。目前，KDX窗膜有以下产品：凌动美肤系列、KDX隐形车衣、KDX家居膜、光致变色膜、温控智变膜。其中，凌动美肤系列能100%隔绝紫外线，它有三大优势：全波段阻隔紫外线，性能卓越的隔热效果，防紫外线使用时间可长达7年！作为一家本土企业，KDX窗膜通过严苛的工艺和先进的技术，以及贴近消费者需求的产品迅速在鱼龙混杂的窗膜领域站稳脚跟。接下来，KDX窗膜还将通过整合资源以及跨界营销的方式走进广大消费者的视野。据康得新光学膜材料有限公司市场总监王一帆介绍，未来，KDX窗膜将通过线上销售渠道的整合和线下体验店的形式，加大KDX窗膜与消费者的直接接触，同时，在产品层面，通过个性化高端产品的推出，来迭代低端的大众化产品，从而让KDX窗膜实现由“行业品牌向消费者品牌”、由“大众品牌向高端品牌”的转变。";
//		String strD = "2016年全球轿车销量TOP10出炉，卡罗拉、高尔夫继续稳居冠亚军，“千年老三”福克斯被现代Elantra，也就是国内销售的北京现代领动超越，屈居第四。本田全新一代思域叫好又叫座，排名第五，神车“德原朗”凭借在中国大陆单一市场47.9万的夸张销量，排名第七。第一名：丰田 卡罗拉卡罗拉全球卖出131万辆，毫无悬念稳居冠军宝座。值得注意的是，丰田并未把广汽丰田雷凌计入卡罗拉全球销量，否则，卡罗拉全球销量可达147万，无愧家用车之王的称号。第二名：大众 高尔夫大众今年很有希望从老冤家丰田手中抢过“最大汽车集团”的称号，而高尔夫的也紧随卡罗拉，2016年全球销量达到了100.3万辆。第三名：现代 Elantra之所以打上英文，并非小编装逼，而是现代在国内的车系命名太过混乱。这里说的是全新一代Elantra，也就是就是北京现代领动。Elantra在美国和中国这世界两大市场都很受欢迎，全球销量78万，超越即将迎来换代的福克斯，位居第三。第四名：福特 福克斯福克斯是福特有史以来最成功的车型之一，在全球市场都很受欢迎。现款福克斯2010年上市，经过几次改款，但是产品力已经落后于时代需求，特别是油耗硬伤，让越来越注重环保的年轻人无法接受。2017年，第四代全新福克斯将要上市，产品力如何有待观察。第五名：本田 全新思域2015年，第十代本田思域在美国上市，获得了媒体的广泛好评；2016年在国内上市，其外观、性能确实惊艳，可以说叫好又叫座，加价提车成为普遍现象。在产品切换时期，本田思域全球销量达到了69万，可以说已经非常成功。2017年，这款车销量很可能继续攀升，入围三甲也不是没可能。第六名：大众 Jetta如果评选“最具话题性”车型，大众Jetta，也就是一汽-大众速腾绝对是当之无愧的No.1。沸沸扬扬的“断轴门”对销量似乎并未产生影响。2016年，速腾在国内销量达到了34万辆，同比增幅高达22%。第七名：大众 朗逸念叨了多年的“大国崛起”，在这款车上得到了印证。朗逸看起来是TOP10榜单最不起眼的，却是唯一一款“非全球车”，只在国内市场销售，就能够挤进TOP10，上汽大众对国人购车心理的把握确实到位。第八名：马自达3 昂克赛拉如果是颜值TOP10，马自达3，也就是国内销售的昂克赛拉，将很有希望艳压群芳，夺取冠军。2016年全球销量46.3万，也是小编蓄谋已久想要购买的梦想座驾。必须两厢的！第九名：雪佛兰 科鲁兹雪佛兰科鲁兹，是榜单中除了福克斯之外，另一款销量大幅下跌的车型。2016年全球销量45万，比去年少买了11万辆，同比狂泻19%；2016年国内销量18.9，同比减少23%，已经跌出国内轿车销量前十。第十名：斯柯达明锐斯柯达Octavia，也就是国产明锐，能够入围榜单多少让人有些意外。毕竟，这款车在国内不太受待见，命名产品力强于速腾，价格还更低，但是销量就是只有速腾的一半。偏爱“德国品质”又不想买大众的朋友，强烈推荐。话说，这款车在德国本土表现可是要强过速腾！文章标签：全球轿车排行榜";
		String strE = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐今日正式上市，售11.57-20.07万元。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：江淮星锐售价 江淮星锐 江淮二代星锐";
		String strF = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐将于今日正式上市。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：新车上市";
		
		long start = System.currentTimeMillis();
		double result = EditDistance.editDis(strE,strF);
		long end = System.currentTimeMillis();
		System.out.println("当前花费时间："+(end - start));
		if(result>=0.3){
			System.out.println("相似度很高: " + result);
		}else{
			System.out.println("相似度不高: " + result);
		}
	}
	@Test
	public void testJaccardImprove(){
		String strE = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐今日正式上市，售11.57-20.07万元。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：江淮星锐售价 江淮星锐 江淮二代星锐";
		String strD = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐将于今日正式上市。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：新车上市";
		
		long start = System.currentTimeMillis();
		JaccardImprove jaccard = JaccardImprove.getInstance();
		System.out.println(jaccard.jaccard(strD, strE));
		long end = System.currentTimeMillis();
		System.out.println("当前花费时间："+(end - start));
	}
	@Test
	public void testJaccard(){
		String strE = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐今日正式上市，售11.57-20.07万元。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：江淮星锐售价 江淮星锐 江淮二代星锐";
		String strD = "星锐是江淮着力打造的欧系客车系列，凭借较高的性价比取得了不错的成绩。网通社从江淮汽车官方获悉，第二代江淮星锐将于今日正式上市。新车外观整体延续现款设计风格，继续推出长、短轴距版供消费者选择。动力方面将搭载1.9CTI、2.7CTI柴油发动机和2.0T汽油发动机。前脸造型圆润饱满，硕大的中网占据了车头很大的面积，流线型的大灯边缘上挑，与车身线条融合在了一起。尾部设计比较传统，采用了对开门的设计，更方便装载货物。在安全配置上，二代星锐装备有ABS、EBD、ESC（车身稳定控制系统）、TCS（牵引力控制系统）、AYC（主动横摆控制系统）、HBA（紧急制动系统）和HAS（坡道起步系统），发动机防盗、中控门锁、倒车雷达和真皮多功能方向盘的搭载让驾驶更加轻松。短轴版可选2.0T汽油发动机和1.9CTI柴油发动机；长轴版可选2.7CTI柴油发动机和康明斯2.8T柴油发动机，全部匹配六速手动变速箱。此次江淮将先推出前三种动力组合，康明斯发动机会在随后推出。文章标签：新车上市";
		
		long start = System.currentTimeMillis();
		System.out.println(Jaccard.jaccard(strD, strE));
		long end = System.currentTimeMillis();
		System.out.println("当前花费时间："+(end - start));
	}
}