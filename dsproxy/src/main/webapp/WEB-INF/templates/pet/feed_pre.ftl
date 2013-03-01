<#-- 宠物喂养界面 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if err_msg?exists>
<#if err_msg.code = 0>喂养宠物成功!<#elseif err_msg.text?exists>${err_msg.text}<#else>喂养宠物失败!</#if><br/>
<#else>小技巧：宠物饥饿值会影响到技能的加成,饥饿值越高则技能加成度越高.<br/></#if>
<#if player_pet?exists>	
	<#if player_pet.pet.namePinyin?exists><img alt="" src="${img_server}/${player_pet.pet.namePinyin}.gif" /><br/></#if>
	名称:${player_pet.customName}<br/> 
	等级:${player_pet.level}<br/>
	饥饿:${player_pet.hungry}/500<br/>
	${player_pet.customName}:<#if pet_say?exists>${pet_say}<#else>....</#if><br/>
	请选择宠物食品:
	<#if items?exists>
		<br/>
		<#list items as playerItem>
			<@a href="/p?i_I/${pid}/${playerItem.id}/"/>
			</a> <@a href="/p?pe_F/${pid}/${player_pet.id}/${playerItem.id}/"/>使用</a>
			${playerItem.item.name}
			<#if (playerItem.amount > 0) >x${playerItem.amount}</#if>
			<br/>
		</#list>
	<#else>
		<br/>没有宠物食品<br/>
	</#if>
	<@a href="/p?ma_BGN/${pid}/32"/>购买宠物粮食</a><br/>
	你必须到宠物商人那里才能购买到普通宠物食品,宠物商人在某些城市的杂货店.<br/>
</#if>
<br/>
<@goPet/>
<@gogame/>
</p></card>
</wml>