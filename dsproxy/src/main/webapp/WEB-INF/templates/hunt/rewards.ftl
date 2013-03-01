<#assign ls = []>
<#macro itemlist mylist> 
悬赏列表:<br/>
		<#if (page_objs?size >0)>
		<#list page_objs as obj>
			<@a href="/p?h/${pid}/take/${label_npc.id}/${obj.id}"/>
		 	${obj.issuerName}: 杀 ${obj.targetName} (${obj.bonusGold}个${rewardItem.name})<br/>
		 	</a>
		</#list>
			<#if (page>0)><@a href="/p?h/${pid}/list/${label_npc.id}/${page-1}"/>上页.<br/></a></#if>
			<#if (page<total_page-1)><@a href="/p?h/${pid}/list/${label_npc.id}/${page+1}"/>下页.<br/></a></#if>
			<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#else>
		没有人悬赏<br/>
	</#if>
<@goback/>
</#macro>