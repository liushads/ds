<#include "/include/header.ftl">
<card title="${game_title}"><p>
你现在有：<br/>
${resell_gold}倒卖币（10万起兑）<br/>
兑换比例：1万=1游戏币<br/>
今日还可以兑换：${exchange_remain_gold}游戏币<br/>
你想兑换：<br/>
<input type="text" name="target_gold" value="10"  maxlength="5" size="5" ></input>万游戏币（输入整数）最大100W<br/>
<anchor>确定兑换
	<@go href="/p?rs_EGG/${pid}/" method="post"/>
    	<postfield name="1" value="$target_gold" />
	</go>
</anchor><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>