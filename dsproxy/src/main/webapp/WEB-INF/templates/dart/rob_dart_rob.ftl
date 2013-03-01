<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<@a href="/p?d_SD/${pid}/in/"/>继续劫镖</a><br/>
<#if label_succ?exists>
	${label_succ}<br/>
<#else>
	失败<br/>
	建议更换推荐装备或强化装备再来劫镖<br/>
</#if>
${label_vs}
战斗回放：关闭/打开<br/>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">