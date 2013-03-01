<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
今日银价：1金兑换${today_silver}银<br/>
<#if (player.gold > 0) || (player.advGold > 0)> 
你可以将账户中的金贝兑换成银贝。<br/>
当前账户信息：<br/>
<@showGold player/>
请输入要兑换的金贝数量:<br/>
<input type="text" name="amount" value="1" format="*N"></input><br/>
<anchor>传送
        <@go href="/p?ma_BS/${pid}" method="post"/>
        	<postfield name="1" value="$amount" />
        </go>
</anchor><br/>	
<#else>
你没有金，<@a href="/p?pa_I/${pid}/1/"/>充值获得金</a><br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>