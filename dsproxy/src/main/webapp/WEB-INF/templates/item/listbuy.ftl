<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if last_npc?exists>${last_npc.name}:</#if>
<#if item?exists>
	你买下了${amount}个${item.name},花费
	<#if buyType?exists && (buyType==1 || buyType==2)>${item.gold/100*amount}金<#else>
	${item.price*amount}铜。</#if><br/>	
	负重:${player.room}/${player.dyn.maxRoom}<br/>
	账户余额:<@showMoney player.copper />,${player.gold/100}金锭,${player.advGold/100}金票<br/>
	你还想买点什么?<br/>
<#else>
	你想买点什么呢?<br/>
</#if>
<#if (page_objs?exists && page_objs?size > 0)>
	<#list page_objs as item>
	<#if item.type!=1>
		<@a href="/p?i_BN/${pid}/${item.id}"/>购买</a>
	<#else>
		<@a href="/p?i_BI/${pid}/${item.id}/1"/>购买</a>	
	</#if>
	<@a href="/p?i_II/${pid}/${item.id}/n"/>${item.name}</a>(${item.price}铜)<#if item.gold &gt; 0>(${item.gold/100}金)</#if><#if item.type == 11 || item.type==5 || item.type==3 || item.type==13 ><#if item.hp &gt; 0 >(${item.hp}体力)</#if><#if item.mp &gt; 0 >(${item.mp}内力)</#if></#if><#if item.type ==1>(${item.level}级)</#if><br/>
	</#list>
	<@a href="/p?ma_I/${pid}/1/"/>高级道具</a><br/>
	<#if (page>0)><@a href="p?i_LB/${pid}/${npc_id}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?i_LB/${pid}/${npc_id}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#if (total_page>1)>
         跳到第<input type="text" name="page" value="${page+1}" format="*N" size="3"/>页
	<anchor>GO
		<@go href="/g?i_LB/${pid}/" method="post"/>
			<postfield name="1" value="${npc_id}" />
	        <postfield name="2" value="page_$page" />
	    </go>
	</anchor>
	<br/>
	</#if>
<#else>
抱歉! 我这里暂时没有什么东西可以卖给你.<br/>
</#if>
<@gofacility/>
</p></card>
</wml>