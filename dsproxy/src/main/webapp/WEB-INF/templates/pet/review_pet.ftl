<#-- 查看宠物详细信息 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
<#if player_pet?exists>	
	<#-- 状态:<#if player_pet.isUse = true>跟随<#else>休息</#if><@petStateOp player_pet/><br/> -->
	<@showPet player,player_pet/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>