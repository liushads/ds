<#macro showNameUrl playeritem>
	<@a href="/p?i_I/${playeritem.playerId}/${playeritem.id}/"/>${playeritem.item.name}
	(${playeritem.item.level}级)
	<#if playeritem.item.type != 1>X${playeritem.amount}</#if>
	<#if playeritem.isUse == 1>(装)</#if>
	</a>
</#macro>

<#macro showPlayerItem playeritem>
	<#if playeritem.item.icon?exists && playeritem.item.icon != "">
	<img src="${playeritem.item.icon}" />
	</#if>
	名称：${playeritem.item.name}<br/>
	说明：${playeritem.item.description}<br/>
	使用等级：${playeritem.item.level}<br/>
	价格：<#if playeritem.item.gold==0>${playeritem.item.price}铜<#else>${playeritem.item.gold/100}金</#if><br/>
	负重：${playeritem.item.room}<br/>	
	<#if (playeritem.item.position == 9)>
	体力：${playeritem.currHp}/${playeritem.item.hp}<br/>
	<#elseif playeritem.item.hp &gt; 0>
	体力：${playeritem.item.hp}<br/>
	</#if>
	<#if playeritem.item.attackMax != 0>
	攻击：${playeritem.item.attackMin}-${playeritem.item.attackMax}<br/>
	</#if>
	<#if playeritem.item.defence != 0>
	防御：${playeritem.item.defence}<br/>
	</#if>
	<#if playeritem.item.crit != 0>
	暴击：${playeritem.item.crit/10000}%<br/>
	</#if>
	<#if playeritem.item.parry != 0>
	格挡：${playeritem.item.parry/10000}%<br/>
	</#if>
	<#if playeritem.item.constitution != 0>
	体质：${playeritem.item.constitution}<br/>
	</#if>
	<#if playeritem.item.forces != 0>
	力量：${playeritem.item.forces}<br/>
	</#if>
	<#if playeritem.item.agility != 0>
	敏捷：${playeritem.item.agility}<br/>
	</#if>
	<#if playeritem.item.intelligence != 0>
	智力：${playeritem.item.intelligence}<br/>
	</#if>
	<#if (playeritem.item.buffNum > 0 && playeritem.buffRands?exists &&  playeritem.buffRands?size > 0)>
		附加属性：<br/>
		<@showBuffRands playeritem.buffRands,"0"/>
	</#if>
	<#if (playeritem.currHoleAmount>0)>
	镶嵌(<#if playeritem.appends?exists>${playeritem.appends?size}<#else>0</#if>/${playeritem.currHoleAmount})<br/>
		<#if playeritem.appends?exists && (playeritem.appends?size>0)>
			<#list playeritem.appends as append>
				<@showSlaverUrl append.item/>:
					<#if append.item.attackMax != 0>攻击+${append.item.attackMin} 
					</#if><#if append.item.defence != 0>防御:+${append.item.defence}
					</#if><#if append.item.agility?exists && append.item.agility != 0>敏捷:+${append.item.agility}
					</#if><#if append.item.hp != 0>体力:+${append.item.hp}
					</#if><br/>
			</#list>
		<#else>
		无镶嵌<br/>
		</#if>
	</#if>
</#macro>

<#macro showSlaverUrl item>
	<@a href="/p?i_IS/${pid}/${item.id}/"/>${item.name}</a>
	</#macro>
<#macro showBuffRands buffRands,lock>
	<#if buffRands?exists && (buffRands?size > 0)>
		<#list buffRands?values as buff>
			${buff.buffRand.buffType.name}：
			<#if buff.buffRand.buffType.type == 4 || buff.buffRand.buffType.type == 5>
				${buff.buffRand.buffValue/100}%
			<#else>
				${buff.buffRand.buffValue}
			</#if>
			<#if lock != "0">
				<#if buff.lock == "0">
					<@a href="/p?i_LBR/${pid}/${lock}/${buff.buffRand.buffTypeId}/${buff.buffRand.id}/"/>锁定</a>
				<#else>
					解锁
				</#if>
			</#if>
			<br/>
		</#list>
	<#else>
	无<br/>
	</#if>
</#macro>
<#macro showBuffRandsCompare srcbuffRands,buffRands>
	<#if buffRands?exists && (buffRands?size > 0)>
		<#list buffRands?values as buff>
			${buff.buffRand.buffType.name}：
			<#if buff.buffRand.buffType.type == 4 || buff.buffRand.buffType.type == 5>
				${buff.buffRand.buffValue/100}%
			<#else>
				${buff.buffRand.buffValue}
			</#if>
			<@compare srcbuffRands,buff/><br/>
		</#list>
	<#else>
	无<br/>
	</#if>
</#macro>

<#macro compare srcbuffRands,buff>
<#if srcbuffRands?exists && (srcbuffRands?size > 0)>
	<#list srcbuffRands?values as srcBuff>
		<#if buff.buffRand.buffTypeId = srcBuff.buffRand.buffTypeId>
			<#if (buff.buffRand.buffValue>srcBuff.buffRand.buffValue)>
			↑
			<#elseif (buff.buffRand.buffValue<srcBuff.buffRand.buffValue)>
			↓
			</#if>
		<#break>
		</#if>
	</#list>
</#if>
</#macro>
<#macro viewUseArm useItems player>
<#if label_positions?exists>
	<#list label_positions?values as position>
		${position.name}:<@output useItems,"${position.id}", player,0,position/><br/>
	</#list>
</#if>

<#if (itemSuitList?exists && itemSuitList?size > 0)>
<#list itemSuitList as itemSuit>
套装:+<@a href="/p?i_SIS/${pid}/${itemSuit.id}/"/>${itemSuit.name}</a><br/>
</#list>
</#if>
</#macro>

<#macro output useItems pos player index position>
	<#if useItems[pos]?exists && (useItems[pos]?size > index)>	
		<#local playerItem = useItems[pos][index]>	
		<#if player.status=1>
<@a href="/p?i_I/${pid}/${playerItem.id}/${player.id}"/><@showName playerItem/></a>
			<#else>
				<@showName playerItem/>
			</#if>
			<#if pid == player.id><@a href="/p?i_UnA/${pid}/${playerItem.id}/"/>卸</a></#if>
	<#else>
	<#if pid == player.id><@a href="/p?i_LA/${pid}/${position.id}/"/>穿</#if></a>
	</#if>
</#macro>
<#macro showName playerItem>
<@outStar playerItem/>${playerItem.item.name}<#if playerItem.improveLevel != 0 >+${playerItem.improveLevel}</#if></#macro>
<#macro outStar playerItem ><#if playerItem.starLevel?exists && playerItem.starLevel != 0>
<#local times = playerItem.starLevel><#list 1..times as i>☆</#list></#if></#macro>



<#--显示道具信息-->
<#macro showItem item>
	<#if item.icon?exists>
	<img alt="" src="${img_server}/${item.namePinyin}.gif" /><br/>
	</#if>
	名称:${item.name}<br/>
	<#if item.type = 1>
	需求:${item.level}级佩戴<br/>
	</#if>	
	价格:<#if item.gold==0>${item.price}铜<#else>${item.gold/100}金</#if><br/>
	<#if item.hp != 0>
	<#if item.type = 11>
	剩余:${item.hp}<br/>
	<#else>
	体力:${item.hp}<br/>
	</#if>
	</#if>
	<#if item.attackMax != 0>
	攻击:${item.attackMin}-${item.attackMax}<br/>
	</#if>
	<#if item.defence != 0>
	防御:${item.defence}<br/>
	</#if>
	<#if item.agility != 0>
	敏捷:${item.agility}<br/>
	</#if>
	<#if item.speed != 0>
	移动:${item.speed}<br/>
	</#if>
	<#if item.description != " ">
	说明:${item.description}<br/>
	</#if>
	<#if (item.type = 1 && item.itemSubType > 0 && item.itemSubType <= 5) >
	类型:【<@outArmAdept item />】<br/>
	</#if>
	负重:${item.room}<br/>
	<#if item.isExchange = true>
	交易:可交易<br/>
	<#else>
	交易:不可交易<br/>
	</#if>
	丢弃:<#if item.dropable = true>可丢弃<#else>不可丢弃</#if><br/>
	<#--药品类只需要说明-->
	<#if item.type == 3>
		<#return>
	</#if>
	
	镶嵌:<#if (item.maxAppend>0)>0/${item.maxAppend}
	<#else>不可镶嵌</#if><br/>
</#macro>

<#macro showPlayerMoney advGold,gold,copper>
	<#if (advGold >= 0)>钻石${(advGold/100)}</#if>
	<#if (gold >= 0)>水晶${(gold/100)}</#if>
	<#if (copper >= 0)>金币${copper}</#if>
</#macro>
