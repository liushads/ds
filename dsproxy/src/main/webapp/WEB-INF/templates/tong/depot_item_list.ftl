<#include "/include/header.ftl">
<#assign d={"1":"装备","2":"宝石","3":"药品","4":"其他"}>
<#macro showType type target>
	<#if type=target>
		${d[type]}
	<#else>
		<@a href="/p?t_DIL/${pid}/${type}/0"/>${d[type]}</a>
	</#if>
</#macro>
<#-- 显示星 -->
<#macro outStar depotItem ><#if depotItem.starLevel?exists && depotItem.starLevel != 0>
<#local times = depotItem.starLevel><#list 1..times as i>☆</#list></#if></#macro>

<#--显示道具名称-->
<#macro showName depotItem>
<@outStar depotItem/>${depotItem.item.name}<#if depotItem.improveLevel != 0 >+${depotItem.improveLevel}</#if></#macro>

<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
仓库(${tong.depotRoom}/${tong_level.depotNum})拥有以下物品<br/>
【<#list d?keys as key>
	<#if key!="4">
	<@showType key,type/><#if key!="3">|</#if>
	</#if>
</#list>】
<br/>

<#if (page_objs?size>0) >
	<#list page_objs as depot>
		<#if depot.type==1>
			<@a href="/p?t_A/${pid}/${depot.id}/1"/>索要</a><#if tong_player?exists && tong_player.level==1> <@a href="/p?t_S/${pid}/${depot.id}/1"/>贱卖</a></#if> <@a href="/p?t_II/${pid}/${depot.id}/vd"/><@showName depot/>(${depot.item.level})</a>  <br/>
		<#else>
			<#if depot.amount==1><@a href="/p?t_A/${pid}/${depot.id}/1"/>索要</a><#else><@a href="/p?t_AM/${pid}/${depot.id}"/>索要</a></#if><#if tong_player?exists && tong_player.level==1> <@a href="/p?t_S/${pid}/${depot.id}/1"/>贱卖</a></#if> <@a href="/p?t_II/${pid}/${depot.id}/vd"/>${depot.item.name?if_exists}(${depot.item.level})</a> <#if (depot.amount>1)>x${depot.amount}</#if>  <br/>
		</#if>
  	</#list>
  	<#if (page>0)><@a href="/p?t_DIL/${pid}/${type}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_DIL/${pid}/${type}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
	仓库中没有${d[type]}	
</#if>
<br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>
