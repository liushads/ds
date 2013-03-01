<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续</a><br/>
	<img alt="" src="${img_server}/tan.gif" />恭喜你获得了在线礼包!<br/>
	<#if givings?exists>
	<#list givings as giveItem>
		你已在线${giveItem.onlineTime/60}分钟，获得了${giveItem.itemNum}个${giveItem.item.name}.<br/>
		<#if giveItem.item.id == 10286>肉干可以在战斗时放入药品栏处，使用后可增加自己体力<br/></#if>
		<#if giveItem.item.id == 10284>遁地符使用后可快速到达与返回任务地点，在杂货店与商城可以购买到！<br/></#if>
		<#if giveItem.item.id == 10333>小行囊可以扩充物品栏负重，当物品栏负重满则不可以拾取和购买物品，大行囊可以在商城购买<br/></#if>
		<#if giveItem.item.id == 10407>此道具打开后即可获得银，可以用于与玩家之间的交易和赠送<br/></#if>
		<#if giveItem.item.id == 10349>点击宠物-喂养-使用后可恢复宠物的饥饿值<br/></#if>
		<#if giveItem.item.id == 10281>使用此道具可以自动捡物-点击物品-药品-宝莲灯-使用-点击状态-特性-时效-查看剩余时间<br/></#if>
		<#if giveItem.item.id == 10457>当武器耐久小于80%时可自动修复全身装备,此道具有50000点耐久，可以在商城购买到！<br/></#if>
		<#if giveItem.item.id == 47>你可以到物品栏其他项<@a href="/p?i_L/${pid}/4/0"/>孵化</a>宠物蛋来获得一个自己的宠物</#if>
		<br/>
	</#list>
	</#if> 
	<#if nextGiving?exists>
	继续在线${nextGiving.onlineTime/60}分钟，将获得${nextGiving.itemNum}个${nextGiving.item.name}.<br/>
	</#if>
	<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续</a>
</p></card>
</wml>