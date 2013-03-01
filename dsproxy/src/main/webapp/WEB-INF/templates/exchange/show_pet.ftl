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
账户剩余:${gold/100}金票 <#if (gold<player_pet.money)>(<@a href="/p?pa_I/${pid}/1/"/>充值</a>)</#if><br/>
<anchor>购买
    <@go href="/p?ex_PD/${pid}/" method="post"/>
        <postfield name="1" value="${player_pet.playerId}" />
        <postfield name="2" value="${player_pet.id}" />
    </go>
</anchor><br/>
<@gogame/>
</p></card>
</wml>