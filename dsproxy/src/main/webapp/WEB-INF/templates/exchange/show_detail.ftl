<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
<@showPlayerItem player_item/>
交易数量:${player_item.exchangeAmount}<br/>
交易价格:${player_item.exchangePrice/100}金票<br/>
账户剩余:${gold/100}金票 <#if (gold<player_item.exchangePrice)>(<@a href="/p?pa_I/${pid}/1/"/>充值</a>)</#if><br/>
<anchor>购买
    <@go href="/p?ex_D/${pid}/" method="post"/>
        <postfield name="1" value="${player_item.playerId}" />
        <postfield name="2" value="${player_item.id}" />
    </go>
</anchor><br/>
<#else>
	无交易信息<br/>
</#if>
<@goback />
<@gogame/>
</p></card>
</wml> 