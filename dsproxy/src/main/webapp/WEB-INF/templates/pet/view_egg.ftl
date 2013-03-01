<#-- 显示玩家所有的宠物和宠物蛋 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if pet_egg?exists>
	图片:无<br/>
	名称:${pet_egg.item.name}<br/>
	说明:${pet_egg.item.description}<br/>
	等级:${pet_egg.item.level}<br/>
	负重:${pet_egg.item.room}<br/>
	数量:${pet_egg.amount}<br/>
	<@a href="/p?pe_In/${pid}/${pet_egg.id}"/>孵化</a><br/>
<#else>
无<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>