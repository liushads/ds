<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if label_desc?exists>
${label_desc}<br/>
<#else>
游戏账户<br/>
<#if info?exists>
${info}<br/>
</#if>
你现有${player.gold/100}个金锭<br/>
<#if gold==0>
游戏账户${gold}元,<@a href="/p?pa_I/${pid}/2/"/>短信充值</a><br/>
<#else>
你的游戏账户中共有${gold}元,一共可以兑换${gold}个金锭,1元=1金锭。<br/>
</#if>
请输入兑换金锭的个数:<br/>
<input type="text" name="amount" value="1" format="*N"  maxlength="3"></input><br/>
<anchor>兑换
	<@go href="/e?pa_E/${pid}/" method="post"/>
		<postfield name="1" value="${player.userId}" />
		<postfield name="2" value="$amount" />
		<postfield name="3" value="${gold}" />
		<postfield name="4" value="0" />
    </go>
</anchor><br/>	
</#if>
<@a href="/p?pa_I/${pid}/2/"/>返回</a><br/>
</p></card>
</wml>