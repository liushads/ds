<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
【<@a href="/p?n_sr/${pid}/outlist/1"/>装备</a>|
<@a href="/p?n_sr/${pid}/outlist/2"/>宝石</a>|
<@a href="/p?n_sr/${pid}/outlist/3"/>药品</a>|
<@a href="/p?n_sr/${pid}/outlist/4"/>其他</a>】<br/>
<#if (page_objs?size > 0)>
	<#list page_objs as obj>
	 <@a href="/p?n_sr/${pid}/takeout/${obj.id}/${type}"/>取出</a>	<#if obj.starLevel==1>☆</#if><#if obj.starLevel==2>☆☆</#if><#if obj.starLevel==3>☆☆☆</#if>${obj.itemName}<#if (obj.improveLevel>0)>+${obj.improveLevel}</#if><#if (obj.amount>1)>  x${obj.amount}</#if> <br/>
	</#list>
	<#if (page>0)><@a href="p?n_sr/${pid}/outlist/${return}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?n_sr/${pid}/outlist/${return}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#if (total_page>1)>
	</#if>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>


 