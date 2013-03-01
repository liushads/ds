<#include "/include/header.ftl">

<card title="${game_title}">
<p>
<#if sect?exists && uid?exists>
	${sect.heasName}:${sect.intro}<br/>
	攻击：${sect.attack}<br/>
	防御：${sect.defence}<br/>
	血量：${sect.hp}<br/>
</#if>
<@a href="/p?u_CR/${uid}/${sect.id}/"/>我要创建</a><br/>
<@ag href="/p?u_EZ/${uid}/"/>返回选择</a><br/>
<@qqbar/>
</p></card>
</wml>