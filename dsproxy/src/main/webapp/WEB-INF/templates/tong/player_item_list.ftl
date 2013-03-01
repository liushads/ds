<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<#assign d={"1":"装备","2":"宝石","3":"药品"}>
<#macro showType type target>
	<#if type=target>
		${d[type]}
	<#else>
		<@a href="/p?t_PIL/${pid}/${type}"/>${d[type]}</a>
	</#if>
</#macro>
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
你可以捐献道具或者钱给帮会,捐献道具的属性将会保留<br/>
仓库负重:${tong_level.depotNum},当前负重:${tong.depotRoom}<br/>
【<#list d?keys as key>
	<@showType key,type/><#if key_has_next>|</#if>
</#list>
】
<br/>

<#if (page_objs?size>0) >
	<#list page_objs as playerItem>
		<#if playerItem.amount==1>
			<@a href="/p?t_D/${pid}/${playerItem.id}/${playerItem.item.id}/${playerItem.item.type}/1"/>捐</a>
		<#else>
			<@a href="/p?t_DM/${pid}/${playerItem.id}"/>捐</a>
		</#if>
	 	<@a href="/p?t_PII/${pid}/${playerItem.playerId}/${playerItem.id}"/><@showName playerItem/></a>
	 	<#if (playerItem.amount>1)>x${playerItem.amount}</#if>
		<br/>
  	</#list>
  	<#if (page>0)><@a href="/p?t_PIL/${pid}/${type}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_PIL/${pid}/${type}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
	你没有${d[type]}可以捐赠。
</#if>
<br/>

请输入捐献的金钱数(每次最多只能捐献100银)：<br/>
<input type="text" name="money" value="" ></input>(银)<br/>
<anchor>确定
	<@go href="/p?t_DC/${pid}/" method="post"/>
		<postfield name="1" value="$money" format="*N" maxlength="3"/>
	</go>		
</anchor><br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>
