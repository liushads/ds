<#-- 可以进行强化的宝石 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if label_improve?exists>
${player_item.item.name} +${player_item.improveLevel}->+${label_improve.improveLevel}<br/>
基础成功率:${label_improve.probability}%以上<br/>
费用:<#if (label_improve.gold > 0)>${label_improve.gold/100}金</#if><#if (label_improve.copper > 0)>${label_improve.copper}铜</#if><br/>
【必需道具】:<br/>
<#if label_need_items?exists && (label_need_items?size > 0)>
	<#list label_need_items as itemVo>
		${itemVo.name}x${itemVo.num}(拥有:${itemVo.playerNum}) <@a href="/p?ma_I/${pid}/1/"/>购买</a><br/>
	</#list>
<#else>
无<br/>
</#if>
【可附加道具】(不使用也能进行装备强化)<#if label_auto?exists && label_auto><@a href="/p?p_SS/${pid}/im/1/${player_item.id}/"/>自动使用</a><#else><@a href="/p?p_SS/${pid}/im/0/${player_item.id}"/>关闭自动使用</a></#if><br/>
<#if label_spec_items?exists && (label_spec_items?size > 0)>
	<#list label_spec_items as itemVo>
		${itemVo.name}x${itemVo.num}(保护装备强化失败装备等级不回落，拥有:${itemVo.playerNum}) <br/>
	</#list>
<#else>
无<br/>
</#if>
<#if label_auto?exists && label_auto>
是否使用	: 
<select name="spec" > 
	<option value="0">否</option>
	<option value="1">是</option>
</select><br/>
</#if>
使用<input type="text" name="xingyun" value="0" format="*N" size="3"/>个幸运石<br/>
<anchor>强化
	<@go href="/p?i_Im/${pid}/" method="post"/>
		<#if label_auto?exists && label_auto>
			<postfield name="1" value="$spec" />
		<#else>
			<postfield name="1" value="1" />
		</#if>
		<postfield name="2" value="${player_item.id}" />
		<postfield name="3" value="$xingyun" />
	</go>
</anchor>
<br/>
【建议】:进行高阶强化时最好同时使用多个幸运石。<br/>
<#else>
不能强化<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 