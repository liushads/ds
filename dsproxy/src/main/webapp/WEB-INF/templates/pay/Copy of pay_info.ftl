<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<a href="/notice.wml">短信充值未到账解决办法</a><br/>
<#assign d={"1":"手机充值卡","2":"Q币","3":"短信"}>
<#macro showType type target>
<#if type=target>
${d[type]}
<#else>
<@a href="/p?pa_I/${pid}/${type}/"/>${d[type]}</a>
</#if>
</#macro>
<#if desc?exists>
您的银贝或者金锭/金票不足，您可以选择以下方式充值金锭/金票，或者在商城用金锭/金票<@a href="/p?ma_BGN/${pid}/46"/>购买银矿石</a>来获取银；<br/>
</#if>
金票和金锭充值<br/>
<@a href="/ga?pa_A/${pid}/${player.userId}/"/>游戏账户</a><br/>
你现有:<br/>
<#if player?exists>
<@showGold player/>
</#if>
<#list d?keys as key>
	<@showType key,return_type/><#if key_has_next>|</#if>
</#list><br/>
<#--Q币-->
<#if return_type = "2">
1金锭=1QB,请输入你要购买的金锭数(1-99)<br/>
<input type="text" name="amount" value="10" format="*N" maxlength="2"></input><br/>
<anchor>提交
	<go href="/qb" method="post">
	    <postfield name="uid" value="${player.userId}" />
	    <postfield name="pid" value="${player.id}" />
        <postfield name="retUrl" value="<@encodeUrl "/p?pa_R/${pid}/", userKey, Md5Key/>" />
        <postfield name="num" value="$amount" />
    </go>
</anchor><br/>
特别说明:<br/>
1.QB每日消费上限是1000,如果需要继续充值请选择手机充值卡。<br/>
<@goback/>
<@gogame/>
<#--短信-->
<#elseif return_type="3">
资费2元/条(不含通信费)<br/>
【每个手机号24小时内最高消费10元，若充值不成功请换其他方式充值。】<br/>
短信充值2元/次,你可以通过<@a href="/ga?pa_A/${pid}/${player.userId}/"/>游戏账户</a>中的兑换功能将钱兑换成游戏中的金锭：<br/>
1.移动用户发短信“20,QQ号码”至106617003436 (免信息费) ,请按照下行提示语进行关联手机。已关联用户下次充值无需再次关联。<br/>
2.关联手机后，用关联手机发短信20到106617009，资费2元/次（不含通信费）。<br/>
3.收到需要回复的短信请务必按照短信提示语回复，否则会导致您的手机扣费而收不到相应金锭。<br/>
请注意:目前暂不支持山东、海南、西藏、江西、辽宁、北京、广西、青海、上海、云南用户进行短信支付。<br/>
特别说明:<br/>
1. 根据短信回复,你将获得2个斗神OL金锭.<br/>
2."QQ号码"是你正登陆斗神OL的QQ号码<br/>
3.充值达消费上限后可能提示已达上限无法继续购买，敬请玩家留意自己的消费情况。 <br/>
温馨提示:短信资费2元/条(不含通信费),客服: 95105789.<br/>
<@goback/>
<@gogame/><br/><br/>
<#--电话卡-->
<#elseif return_type="1">
您将使用手机充值卡充值金票，1金票1元,您购买的金票数量由您的充值卡面额决定；<br/>
<anchor>去充值
	<go href="/szf" method="post">
	    <postfield name="uid" value="${player.userId}" />
	    <postfield name="pid" value="${player.id}" />
        <postfield name="retUrl" value="<@encodeUrl "/p?pa_R/${pid}/", userKey, Md5Key/>" />
    </go>
</anchor><br/>
特别说明:<br/>
1、大家充值时候请注意选择充值面额，核对选择的面额和卡本身的面额一致。<br/>
2、暂不支持联通的手机充值卡。<br/>
<@goback/>
<@gogame/><br/>
<#--wap支付-->
<#elseif return_type="4">
您将购买2个金贝(动感炫彩金贝)<br/>
<@a href=""/>">确认支付</a><br/>
<@goback/>
<@gogame/><br/>
[温馨提示]<br/>
1.本业务名称为动感炫彩魅力视界，由掌中星与腾讯公司合作开发提供（资费：2元/次）<br/>
2.每次支付可获赠金贝2个<br/>
3.每个QQ号或者手机号，每天通过手机网页充值金贝的金额上限是15元，每月的上限是30元。<br/>
4.本业务仅支持中国移动用户，其他用户请使用Q币等方式购买<br/>
5.客服电话：95105785<br/>
</#if>
</p></card>
</wml>