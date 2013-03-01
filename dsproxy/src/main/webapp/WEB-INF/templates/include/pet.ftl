<#-- 宠物状态操作 -->
<#macro petStateOp pet>
	<#if pet.isUse = false>
		<@a href="/p?pe_FU/${pid}/${pet.id}/"/>跟随</a>	
	<#else>
		<@a href="/p?pe_FU/${pid}/${pet.id}/"/>休息</a>
	</#if>
</#macro>

<#-- 显示宠物 -->
<#macro showPet player player_pet>
<#if player.id == player_pet.playerId><@showSelfPet player_pet/>
<#else><@showOtherPet player_pet/></#if>
</#macro>


<#-- 显示自己宠物 -->
<#macro showSelfPet player_pet>	
	<#if player_pet.pet.namePinyin?exists><img alt="" src="${img_server}/${player_pet.pet.namePinyin}.gif" /><br/></#if>
	${player_pet.customName}:<#if pet_say?exists>${pet_say}<#else>主人好!</#if><br/>
	等级:${player_pet.level}<br/> 
	饥饿:${player_pet.hungry}/500<br/>
	经验:${player_pet.exp}/${player_pet.dyn.maxExp}<br/>
	攻击:${player_pet.attack}<br/> 
	防御:${player_pet.defence}<br/> 
	敏捷:${player_pet.agility}<br/>	
	<#--<#if player_pet.isUse = true>跟随<#else>休息</#if>--><@petStateOp player_pet/> <@a href="/p?pe_FP/${pid}/${player_pet.id}/"/>喂养</a> <#if player_pet.isUse = false><@a href="/p?pe_DrP/${pid}/${player_pet.id}"/>丢弃</a></#if>
	 <@a href="/p?pe_MT/${pid}/${player_pet.id}/"/>技能</a> <@a href="/p?pe_RPr/${pid}/${player_pet.id}/"/>洗点</a><br/>
	 
<#--
	<#if (player_pet.talents?exists && player_pet.talents?size > 0) >
		<#list player_pet.talents?values as talent>
			<@a href="/p?pe_ReT/${pid}/${player_pet.id}/${talent.id}/"/>${talent.name}</a><br/>		
		</#list>
	<#else>
		您的宠物没有天赋,宠物每升25级可以开启一个天赋<br/>
	</#if>
 -->	
</#macro>

<#-- 显示其他玩家宠物 -->
<#macro showOtherPet player_pet>
	<#if player_pet?exists>
	<#if player_pet.pet.namePinyin?exists><img alt="" src="${img_server}/${player_pet.pet.namePinyin}.gif" /><br/></#if>
	等级:${player_pet.level}<br/> 
	经验:${player_pet.exp}/${player_pet.dyn.maxExp}<br/>
	饥饿:${player_pet.hungry}/500<br/>
	攻击:${player_pet.attack}<br/> 
	防御:${player_pet.defence}<br/> 
	敏捷:${player_pet.agility}<br/>
        【天赋列表】<#if (player_pet.talents?exists && player_pet.talents?size > 0) >
		<br/>
		<#list player_pet.talents?values as talent>
			${talent.name}<br/>		
		</#list>
	<#else>无<br/></#if>	<#else>无<br/></#if>
</#macro>

<#-- 显示宠物天赋 -->
<#macro showPetTalent talent>
	名称:${pet_talent.name}<br/>
	描述:${pet_talent.description}<br/>
</#macro>

<#-- 返回宠物入口 -->
<#macro goPet>
	<@a href="/p?pe_L/${pid}/"/>宠物界面</a><br/>
</#macro>

<#-- 返回宠物入口 -->
<#macro showPetUrl player><#if player.usedPet?exists><@a href="/p?pe_VoP/${pid}/${player.usedPet.id}/${player.id}/"/>
${player.usedPet.pet.name}</a>
<#else>无</#if>
</#macro>