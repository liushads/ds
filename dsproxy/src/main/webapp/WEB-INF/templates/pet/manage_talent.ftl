<#-- 宠物孵化 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if (err_msg?exists && err_msg.code != 0)>
<#if err_msg.text?exists>${err_msg.text}<#else>遗忘技能失败!</#if><br/>
<#else>
<#if player_pet?exists>
	<#if desc?exists>${desc}<br/></#if>
	<#if (player_pet.talents?size <= 0)>
	你的宠物还没有技能,宠物每25级可以开启一个技能.<br/>	
	</#if>
	<#if player_pet.talents?size < talent_size && (talent_size >= 1)>
		<@a href="/p?pe_En/${pid}/${player_pet.id}/"/>开启技能</a><br/>
	</#if>
	<#if (player_pet.talents?exists && player_pet.talents?size > 0) >
		【${player_pet.customName}技能列表】
		<br/>
		使用"洗灵宝珠"可以遗忘宠物技能<br/>
		<#list player_pet.talents?values as talent>			
			<@a href="/p?pe_FPr/${pid}/${player_pet.id}/${talent.id}/"/>遗忘</a>
			<@a href="/p?pe_ReT/${pid}/${player_pet.id}/${talent.id}/"/>${talent.name}</a><br/>	
		</#list>
	<#else>
	</#if>
</#if>
</#if>
<@a href="/p?pe_L/${pid}/"/>返回</a><br/>
<@gogame/>
</p></card>
</wml>