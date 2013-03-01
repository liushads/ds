<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#-- 显示卖掉的道具信息 -->
<#if desc?exists>
	<#if npc?exists>${npc.name}:</#if>
	<#if desc?exists>${desc}</#if>,收入${copper}铜<br/>
	负重:${player.room}/${player.dyn.maxRoom}<br/>
	铜币:${player.copper}铜
	<br/>
</#if>
<#--显示分类-->
包裹物品:<br/>
【	<#list d?keys as key>
		<@showType key,itype /><#if key_has_next>|</#if>
	</#list>
】
<br/>
<#if page_objs?size=0>
	没有物品可以卖出。<br/>
<#else>
<#if itype?exists && itype='1'>
<@a href="/p?i_SAI/${pid}/0"/>一键卖装</a><br/>
</#if>
	<#list page_objs as playerItem>
		<@showSell playerItem />
	</#list>
	<#if (page>0)><@a href="/p?i_LS/${pid}/${itype}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LS/${pid}/${itype}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#if (total_page>1)>
         跳到第<input type="text" name="page" value="${page+1}" format="*N" size="3"/>页
	<anchor>GO
		<@go href="/p?i_LS/${pid}/" method="post"/>
			<postfield name="1" value="${itype}" />
	        <postfield name="2" value="page_$page" />
	    </go>
	</anchor>
	<br/>
	</#if>
</#if>
<br/> 
<@gofacility/>
</p>
</card>
</wml> 