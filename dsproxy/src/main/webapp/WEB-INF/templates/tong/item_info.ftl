<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card id="c1"><p>
<@showItem item />
<#if "vd" == status>
	<#if item.type==1>
		<@a href="/p?t_A/${pid}/${tong_depot.id}/1"/>索要</a><br/>
		<#if tong_player?exists && tong_player.level==1> 
			<@a href="/p?t_S/${pid}/${item.id}/1"/>贱卖</a>
		</#if><br/>
	<#else>
		<@a href="/p?t_AM/${pid}/${tong_depot.id}"/>索要</a><br/>
		<#if tong_player?exists && tong_player.level==1> 
		<@a href="/p?t_SI/${pid}/${tong_depot.id}/${tong_depot.amount}"/>贱卖</a>
		</#if>
		<br/>
	</#if>
<#elseif "ma" == status>
<@a href="/p?t_MAsA/${pid}/allow/${tong_depot.id}"/>同意</a>  
<@a href="/p?t_MAsA/${pid}/refuse/${tong_depot.id}"/>拒绝</a>  
<@a href="/p?t_MAsA/${pid}/chuck/${tong_depot.id}"/>丢弃</a><br/>
</#if>
<@goback />
</p></card>
</wml>