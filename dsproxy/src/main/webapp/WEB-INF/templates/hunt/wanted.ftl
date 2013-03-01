<#include "/include/header.ftl">
<card title="${game_title}"><p>
通缉令列表:<br/>
		<#if (page_objs?size >0)>
		<#list page_objs as obj>
		${obj.issuerName}: 杀 ${obj.targetName} (${obj.bonusGold?number * 100}银贝)
			<@a href="/p?h/${pid}/cancel/${label_npc.id}/${obj.playerMissionId}/"/>
		 	取消
		 	</a>
		 	<br/>
		</#list>
			<#if (page>0)><@a href="/p?h/${pid}/list/${label_npc.id}/${page-1}"/>上页.<br/></a></#if>
			<#if (page<total_page-1)><@a href="/p?h/${pid}/list/${label_npc.id}/${page+1}"/>下页.<br/></a></#if>
			<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#else>
		没有人通缉令<br/>
	</#if>
	<@goback/>
	<@gofacility/>
</p>
</card>
</wml>