<#-- 显示玩家所要装备类物品 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
你当前等级:【${player.level}级】<br/>
装备物品列表:<br/>
<#-- 列出装备 -->
<#if (page_objs?size > 0)>
	<#list page_objs as obj>
	<#--<#if obj.item.level <= player.level>-->
	<#if obj.isUse==1>
	<@a href="p?i_UnA/${pid}/${obj.id}"/>卸下</a>
	<#else>
	<@a href="/p?i_UA/${pid}/${obj.id}"/>使用</a>	
	</#if>
	<@showNameUrl obj/><#if obj.isUse=1>(装)</#if><br/>
	<#--<#else><@showName obj/>(${obj.item.level}级)<br/></#if>-->
	</#list>
	<#if (page>0)><@a href="/p?i_LA/${pid}/${return_type}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LA/${pid}/${return_type}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
	没有可使用装备<#if (return_type?exists && return_type = "12" )>,装备马匹可以加快玩家移动速度<#else>。</#if>.<br/>
</#if>
<br/>
<@goback />
<@gogame/>
</p></card>
</wml> 