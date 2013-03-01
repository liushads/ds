<#-- 宠物丢弃 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if player_pet?exists><#-- 丢弃的宠物 -->
	你确认要丢弃${player_pet.customName}?<br/>
	<@a href="/p?pe_Dr/${pid}/${player_pet.id}/"/>确认</a><br/>
	<@goback/>
</#if>
<#if desc?exists>
	${desc}<br/>
	<@goPet/>
</#if>
<@gogame/>
</p></card>
</wml>