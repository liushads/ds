<#-- 显示玩家交易宠物信息 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>

<#if player_pet?exists>
【宠物信息】<br/>
名字:${player_pet.customName}<br/>
等级:${player_pet.level}<br/>
攻击:${player_pet.attack}<br/>
防御：${player_pet.defence}<br/>
敏捷：${player_pet.agility}<br/>
技能：<br/><#if (player_pet.talents?exists && player_pet.talents?size > 0) >
		【${player_pet.customName}技能列表】
		<br/>
		<#list player_pet.talents?values as talent>			
			${talent.name}
		</#list>
	</#if>
</#if>
<br/>
交易价格:${player_pet.money/100}金票<br/>
<@a href="/p?ex_CP/${pid}/${player_pet.id}"/> 撤销</a><br/>
<@gogame/>
</p></card>
</wml>