<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if player_pet?exists && pet_talent?exists>
【${player_pet.customName}】成功开启技能【${pet_talent.name}】<br/>
技能描述:${pet_talent.description}<br/>
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
<@goback/>
<@a href="/p?pe_L/${pid}/"/>返回宠物界面</a><br/>
<#else>
开启技能失败
</#if>
<@gogame/>
</p></card>
</wml>