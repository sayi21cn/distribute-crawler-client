package xu.main.java;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SendMailTest {

	private Logger logger = Logger.getLogger(SendMailTest.class);

	@Test
	public void testSendMail() {

		for (int index = 0; index < 20; index++) {
			logger.error(getMailContent(index));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// try {
		// SendMail.sendHtmlMail("Javax测试新配置邮件发送器", "这里是邮件内容");
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.out.println("邮件发送测试失败！");
		//
		// }

	}

	public String getMailContent(int index) {
		return mailContent.get(index);
	}

	private List<String> mailContent = Arrays.asList("1、有时候真实比小说更加荒诞，因为虚构是在一定逻辑下进行的，而现实往往毫无逻辑可言。", "2、如果你经历过真正的孤立无援，你会明白眼泪是最无用的东西，它无法改变既定的事实，无法扭转绝望的困局。它唯一的好处是宣泄压抑的情绪。", "3、爱对了人固然是运气，若是爱错了，那也叫青春。",
			"4、“人总是这样的矛盾，当你去相信时，被骗的遍体鳞伤；当你习惯性的怀疑时，却偏偏有人那么善良，让你觉得对他们的怀疑其实是自己的内心那么肮脏。” 所以，只能选择相信别人时，不忘记有原则的提防。被别人欺骗时，绝不放弃对其他人的善良，这样才不会对这个世界彻底失望。", "5、自伯之东，首如飞蓬。岂无膏沐，谁适为容。", "6、做人要含蓄点，得过且过，不必斤斤计较，水清无鱼，人清无徒，谁又不跟谁一辈子，一些事放在心中算了。", "7、思念这东西，真的很没道理。不是为了缅怀往事而念念不忘，而是真的念念不忘。",
			"8、是谁，三千繁华，入你眉心；是谁，青山如墨，素衣白发；是谁，菩提树下，一指清音？是谁温暖了谁的岁月，又是谁惊艳了谁的时光。相思缱绻，为你书写一纸信笺。惟愿，经年离去，落英满地，不再伤怀。", "9、需要被爱的人才会拼命付出，但被爱的人却总会忘了回报。", "10、曾经的梦想像错过站的火车，早就不知道去往了哪一站。你还信誓旦旦地以为自己没有长大，结果时间一早就把你甩到了十字路口。",
			"11、人不应该是插在花瓶里供人观赏的静物，而是蔓延在草原上随风起舞的韵律。生命不是安排，而是追求，人生的意义也许永远没有答案，但也要尽情感受这种没有答案的人生。", "12、嫉妒表面上是对别人不满，实际上反映的是对自己不满。意识到自己的不足，才会嫉妒别人。", "13、好像每个人都清楚地知道别人该怎样生活，但却没有一个人知道自己的生活该怎样，就像释梦的老妇人，不知道如何把梦变为现实。",
			"14、有人一路走来顺风顺水，恋爱结婚生子一切看起来毫无波澜，有人生来折腾，二十几岁就尝遍了百种滋味。都对，都好，没有哪个是错的或者是不公平，是你的，你都该把它收起来。", "15、爱都会有从浓转淡的过程，既然开始了，就要学着去珍惜。", "16、人追求的当然不是财富，但必要有足以维持尊严的生活，使自己能夠不受阻扰的工作，能夠慷慨，能夠爽朗，能夠独立。", "17、“不要贸然评价我，你只是知道我的名字，可你不知道我的故事。你只是听闻过我的行为，却不知道我经历过些什么。” ——做好自己已经很难了，何必去八卦别人。",
			"18、恋爱如做人，追求爱的态度，也体现人品。爱一个人，他不爱你，那就把这份爱默默藏在心底吧。一厢情愿地头破血流、肝脑涂地，硬要对方知道你的爱、你的苦、你的伟大，说什么我的爱不用你管，这不是爱，是撒野，是骚扰。有时候，人生是要流着泪、咬着牙，学着做一个沉默而高尚的人。", "19、家庭教育的秘诀：闭上你的嘴，抬起你的腿，走你的人生路，演示给孩子看。所有孩子的模仿力都比奥斯卡影后影帝出色一千倍。", "20、人生如逆旅，我亦是行人，但愿初相遇，不负有心人。");
}