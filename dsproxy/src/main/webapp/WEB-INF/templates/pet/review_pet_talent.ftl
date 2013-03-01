<#-- 查看宠物详细信息 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
<#if pet_talent?exists>	
	${pet_talent.name}<br/>
	<@showPetTalent pet_talent/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>