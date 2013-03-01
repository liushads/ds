<#-- 洗点 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if desc?exists><#-- 提示 -->
	${desc}<br/>
</#if>
<#if player_pet?exists>	
	<@showPet player,player_pet/>
</#if>
<@goPet/>
<@gogame/>
</p></card>
</wml>