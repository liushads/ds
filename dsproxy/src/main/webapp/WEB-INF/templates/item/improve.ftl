<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<#assign showContinue = 1/>
<card title="${game_title}"><p>
<#if label_extra?exists>
	恭喜你强化成功<br/>
	<@a href="/p?i_IMT/${pid}/${player_item.id}"/>继续强化</a><br/>
	${player_item.item.name}(${player_item.item.level}级)+${player_item.improveLevel -1}->+${player_item.improveLevel}<br/>
	<#list label_extra?keys as key>
		<#if (label_extra[key][1] > 0)>
		<#switch key>
           	<#case "getExtraHp">
           	体力
				<#break>
			<#case "getExtraAttack">
			攻击
				<#break>
			<#case "getExtraDefence">
			防御
				<#break>
			<#case "getExtraCrit">
			暴击
				<#break>
			<#case "getExtraParry">
			格挡
				<#break>
			<#case "getExtraConstitution">
			体质
				<#break>
			<#case "getExtraForces">
			力量
				<#break>
			<#case "getExtraAgility">
			敏捷
				<#break>
			<#case "getExtraIntelligence">
			智慧
				<#break>
        </#switch>
        +${label_extra[key][0]}->+${label_extra[key][1]}<br/>
		</#if>
	</#list>
<#else>
强化失败了- -！<br/>
</#if>
<@a href="/p?i_IMT/${pid}/${player_item.id}"/>继续强化</a><br/>
<@goback/>
<@gofacility/>
</p></card>
</wml>