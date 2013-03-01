<#-- 洗点 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if player_pet?exists>	
	你需要道具【${item.name}】才能洗点宠物属性点<br/>
	你确定要洗掉${player_pet.customName}现有属性?<br/>
	<@a href="/p?pe_RP/${pid}/${player_pet.id}/"/>确定</a><br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>