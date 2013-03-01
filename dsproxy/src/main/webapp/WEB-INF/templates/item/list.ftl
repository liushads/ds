<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<#macro showType type target>
	<#if type.id+""=target>
		${type.name}
	<#else>
		<@a href="/p?i_L/${pid}/${type.id}/0"/>${type.name}</a>
	</#if>
</#macro>

<card title="${game_title}"><p>
负重：${player.room}/${player.dyn.maxRoom}<br/>
<@showAllMoney player,1/>
【<#list item_type as key>
	<@showType key,return/><#if key_has_next>|</#if>
</#list>】
<br/>
<#if (page_objs?size > 0)>
	<#list page_objs as obj>
		<@showNameUrl obj/><br/>
	</#list>
	<#if (page>0)><@a href="p?i_L/${pid}/${return}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?i_L/${pid}/${return}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#if (total_page>1)>
         跳到第<input type="text" name="page" value="${page+1}" format="*N" size="3"/>页
	<anchor>GO
		<@go href="/p?i_L/${pid}/" method="post"/>
			<postfield name="1" value="${return}" />
	        <postfield name="2" value="page_$page" />
	    </go>
	</anchor>
	<br/>
	</#if>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>


 