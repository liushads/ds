<#--显示道具-->
<#macro showItem item>	
	<#if item.icon?exists && item.icon != "">
	<img src="${item.icon}" />
	</#if>
	名称:${item.name}<br/>
	说明:${item.description}<br/>
	使用等级:${item.level}<br/>
	价格:<#if item.gold==0>${item.price}铜<#else>${item.gold/100}金</#if><br/>
	负重:${item.room}<br/>	
	<#if item.hp &gt; 0>
	体力:${item.hp}<br/>
	</#if>
	<#if item.attackMax != 0>
	攻击:${item.attackMin}-${item.attackMax}<br/>
	</#if>
	<#if item.defence != 0>
	防御:${item.defence}<br/>
	</#if>
	<#if item.crit != 0>
	暴击:${item.crit/10000}%<br/>
	</#if>
	<#if item.parry != 0>
	格挡:${item.parry/10000}%<br/>
	</#if>
	<#if item.constitution != 0>
	体质:${item.constitution}<br/>
	</#if>
	<#if item.forces != 0>
	力量:${item.forces}<br/>
	</#if>
	<#if item.agility != 0>
	敏捷:${item.agility}<br/>
	</#if>
	<#if item.intelligence != 0>
	智力:${item.intelligence}<br/>
	</#if>
	<#if item.endure != 0>
	耐久:${item.endure}<br/>
	</#if>	
	<#if (item.maxAppend>0)>
	镶嵌(0/${item.maxAppend})<br/>
	</#if>
</#macro>