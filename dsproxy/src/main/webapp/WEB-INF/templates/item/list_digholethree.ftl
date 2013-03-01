<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
	拆卸成功<br/>
	<@a href="/p?i_Dh/${pid}/"/>继续拆卸</a><br/>
<@gofacility/>
</p></card>
</wml> 