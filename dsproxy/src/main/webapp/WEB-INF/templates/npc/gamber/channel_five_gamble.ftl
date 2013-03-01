<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村赌场<br/>
赌神韦小宝<br/>
<#if roundMaxPoints?exists && roundMaxPoints &gt; 0>
上一局开奖结果为：${roundMaxPointStr}(${roundMaxPoints})<br/>
</#if>
本局已经有${num}位玩家参与，累计${totalMoney}银。距离开奖时间还有${leftTime}秒<br/>
<#if maxPoints?exists>
你最大掷骰结果：<br/>
${maxPoints}<br/>
</#if>
你本次掷骰子结果：<br/>
${currentPoints}<br/>

<@a href="/p?n_Gx/${pid}/ch/fv/g/"/>再掷一次骰(1银)</a> <@a href="/p?n_Gx/${pid}/ch/fv/v/"/>刷新</a><br/>
<@a href="/p?n_Gx/${pid}/ch/fv/r"/>玩法规则</a><br/>
<br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>