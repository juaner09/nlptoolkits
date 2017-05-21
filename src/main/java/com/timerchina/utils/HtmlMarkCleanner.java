package com.timerchina.utils;

import org.apache.log4j.Logger;

@SuppressWarnings("unused")
public class HtmlMarkCleanner {
	private static final Logger log = Logger.getLogger(HtmlMarkCleanner.class);
	
	private static final int MATCH_GROUP_SIZE = 1;
	private static final String HTML_IMG_LEFT = "【图片】";
	private static final String HTML_IMG_RIGHT = "【/图片】";
	private static final String HTML_REPLY_QUOTE_LEFT = "《【引用】";
	private static final String HTML_REPLY_QUOTE_RIGHT = "【/引用】》";
	private static final String HTML_REPLY_QUOTE_AUTHOR_LEFT = "【作者】";
	private static final String HTML_REPLY_QUOTE_AUTHOR_RIGHT = "【/作者】";
	
	private static final String HTML_MARK_AND = "&";
	private static final String HTML_MARK_LT = "&lt;";
	private static final String HTML_MARK_GT = "&gt;";
	private static final String HTML_MARK_AMP = "&amp;";
	private static final String HTML_MARK_QUOT = "&quot;";
	private static final String HTML_MARK_REG = "&reg;";
	private static final String HTML_MARK_COPY = "&copy;";
	private static final String HTML_MARK_TRADE = "&trade;";
	private static final String HTML_MARK_ENSP = "&ensp;";
	private static final String HTML_MARK_EMSP = "&emsp;";
	private static final String HTML_MARK_NBSP = "&nbsp;";
	private static final String HTML_MARK_SEPARATOR = "------------------------------------------------------------------";

	private static String processSpicalMark(String content) {
		content = content.replace(HTML_MARK_AMP, "&");
		content = content.replace(HTML_MARK_COPY, "【M版权】");
		content = content.replace(HTML_MARK_EMSP, "__");
		content = content.replace(HTML_MARK_ENSP, "_");
		content = content.replace(HTML_MARK_GT, ">");
		content = content.replace(HTML_MARK_LT, "<");
		content = content.replace(HTML_MARK_NBSP, " ");
		content = content.replace(HTML_MARK_QUOT, "''");
		content = content.replace(HTML_MARK_REG, "【M注册】");
		content = content.replace(HTML_MARK_TRADE, "【M商标】");
		content = content.replace(HTML_MARK_SEPARATOR, "【M分隔符】");
		//处理特殊符号
//		content = content.replaceAll("[^\u4e00-\u9fa5\\d\\w\\p{P}]+", "");

		return content;
	}
	
	private static String matchAttribute (String content, String expr) {
		if("NO".equals(expr)){
			return "NO";
		}
		return RegexTools.getFirstMatchGroupOfFirstMatch(expr, content);
	}

	public static String cleanHtmlMark(String content){
		
		if(CommonTools.isEmpty(content)) return content;
		
		content = processImg(content);
		
		content = postProcessHtmlMark(content);
		
		content = processSpicalMark(content);
		
		return content.trim();
	}

	public static String cleanHtmlMarkForSpecial(String content){
		
		if(CommonTools.isEmpty(content)) return content;
		
		content = processImg(content);
		
		content = postProcessHtmlMark(content);
		
		content = processSpicalMark(content);
		
		content = content.replaceAll("[^\u4e00-\u9fa5\\d\\w\\p{P}]+", "");
		
		return content.trim();
	}

	private static String postProcessHtmlMark(String content) {
		content = content.replaceAll("<br[^>]*?>", "\r\n");
		content = content.replaceAll("<p>", "\r\n");
		content = content.replaceAll("</p>", "\r\n");
		content = content.replaceAll("<script.*?>.*?</script>", "");
		content = content.replaceAll("<style.*?>.*?</style>", "");
//		content = content.replaceAll("<table.*?>.*?</table>", "");
		content = content.replaceAll("=[\"'](.*?)[\"']", "");//处理标签中含有的脚本比如含>符号
		content = content.replaceAll("<[^>]*?>", "");// 处理 <*>
		content = content.replaceAll("'", "''");
		return content;
	}


	private static String processImg(String content) {
		String srcRegex = "<IMG[^>]*?src=(?:\"|')([^>]*?)(?:\"|')[^>]*?(?:on.+=(?:\"|').+;?(?:\"|'))?[^>]*?/?>";
		content = content.replaceAll("<img", "<IMG");
		String imgUrl = matchAttribute(content, srcRegex);
		String replaceContent = HTML_IMG_LEFT + imgUrl + HTML_IMG_RIGHT;
		
		return content.replaceAll(srcRegex, replaceContent);
	}
	
	public static void main(String[] args) {
		System.out.println(cleanHtmlMarkForSpecial("郑爽儿时模仿宋丹丹完整版片段<img render=\\\"ext\\\" src=\\\"http:\\/\\/img.t.sinajs.cn\\/t4\\/appstyle\\/expression\\/ext\\/normal\\/b6\\/doge_org.gif\\\" title=\\\"[doge]\\\" alt=    \\\"[doge]\\\" type=\\\"face\\\" \\/> <a target=\\\"_blank\\\" render=\\\"ext\\\" extra-data=\\\"type=topic\\\"  class=\\\"a_topic\\\" href=\\\"http:\\/\\/huati.weibo.com\\/k\\/%E8%8A%B1%E5%84%BF%E4%B8%8E%E5%B0%91%E5%B9%B4?from=501\\\">#花儿与少年#<\\/a> <a class=\\\"W_btn_b btn_22px W_btn_cardlink\\\"  suda-uatrack=\\\"key=tblog_card&value=click_title:3849382753183090:2012676002-video:2012676002%3A8ba9ee75773c4f4900c69eae477677dc\\\"title=\\\"《花儿与少年远行记》：小小爽妹子变身东北老太太\\\" href=\\\"http:\\/\\/t.cn\\/R2XyHv8\\\"  action-type=\\\"feed_list_url\\\" target=\\\"_blank\\\" ><i class=\\\"W_ficon ficon_cd_video S_ficon\\\">L<\\/i><i class=\\\"W_vline S_line1\\\"><\\/i><em class=\\\"W_autocut S_link1\\\">《花儿与少年远行记》：小小爽妹子变身东北老太太<\\/em><\\/a>"));
	}
}
