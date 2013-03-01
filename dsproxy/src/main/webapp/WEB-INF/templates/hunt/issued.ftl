<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${label_npc.name}：你已经对${label_target}进行了通缉，九天皓令已经发出；在你成功复仇之前请保存好自己的性命….. 
<#if label_items?exists && label_items?size &gt; 0>
<br/>选择你通缉的仇家：
	<#list label_items as item> 
		<br/><@a href="/p?h/${pid}/create/${label_npc.id}/${item.enemyId}"/>
	 	${item.enemyName} (被他杀了${item.killedTimes}次)
	 	</a>
	</#list>
<#else>
<br/>目前没有仇家	
</#if>
<br/><@gofacility/>
</p>
</card>
</wml>
