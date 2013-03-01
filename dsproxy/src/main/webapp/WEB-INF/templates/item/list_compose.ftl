<#-- 显示可以合成的宝石列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#assign animal={"1":"青龙","2":"白虎","3":"朱雀","4":"玄武"}/>
<#macro showAnimal crt dst>
<#if crt=dst>
		${animal[crt]}
	<#else>
		<@a href="/p?i_LC/${pid}/${npc_id}/${crt}/"/>${animal[crt]}</a>
	</#if>
</#macro>
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
10个低级宝石可以合成一个更高等级的宝石，合成一个需要1银.
</#if><br/>
【<#list animal?keys as key>
	<@showAnimal key,return_type/><#if key_has_next>|</#if>
</#list>】
<br/>
<#if (items?exists && items?size > 0)>
<#list items as item>
<@a href="/p?i_LCM/${pid}/${item.id}/${npc_id}/"/>合成</a> <@showItemNoBuy item/><br/>
</#list>
<#else>
目前暂时没有宝石可以合成哟,以后再来.
</#if>
<br/>
<@a href="/p?i_LC/${pid}/${npc_id}/1/"/>返回</a><br/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>