<#-- 显示玩家所有的宠物和宠物蛋 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>

<#if player_pet?exists><#-- 使用的宠物 -->
【跟随上宠物】<br/>
	<@showPet player,player_pet/>
	<@a href="/p?pe_DrP/${pid}/${player_pet.id}/"/>丢弃</a> 
	<@a href="/p?pe_UPN/${pid}/${player_pet.id}/"/> 改名</a><@a href="/p?ex_PS/${pid}/${player_pet.id}/"/> 摆摊</a><br/>
</#if>
<#if pet_list?exists>
	【休息中宠物】<br/>	
	<#list pet_list as playerPet>
	<@petStateOp playerPet/><@a href="/p?pe_ReP/${pid}/${playerPet.id}"/>${playerPet.customName}</a>   <@a href="/p?ex_PS/${pid}/${playerPet.id}/"/>   摆摊</a><br/>
	</#list>
<#else>你还没有宠物,可以通过宠物蛋孵化获得宠物.<br/></#if>
<@a href="/p?pe_I/${pid}/m"/>宠物使用说明</a>
<br/>
<@gogame/>
</p></card>
</wml>