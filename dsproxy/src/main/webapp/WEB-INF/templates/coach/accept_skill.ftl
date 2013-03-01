<#-- 接受师傅传授的技能 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if player_skill?exists>
	您已经成功学习到技能<@showUpgradePreUrl player_skill/>	
<#elseif (err_msg?exists && err_msg.text?exists)>
	${err_msg.text}<br/>
</#if>
<@a href="/p?co_CM/${pid}/"/>返回</a><br/>
<@gogame />
</p>
</card>
</wml>