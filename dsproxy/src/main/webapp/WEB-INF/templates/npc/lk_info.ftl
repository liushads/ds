<#include "/include/header.ftl">
<card title="${game_title}"><p>
守湖人：<br/>
<#if info?exists>
${info}<br/>
</#if>
<#if replyWish?exists>
<@a href="/p?n_lw/${pid}/input/${wishId2}"/>回复</a><br/>
</#if>
<#if t_wish?exists>
你的许愿瓶已经扔出去了，看看哪位有缘人能钓起吧<br/>
【提示】您不能离开钓鱼湖才有可能等到回复<br/>
</#if>
<#if wishId?exists>
【提示】回复需要传话筒，你每天以免费领取两个，若还想回复请到商城购买传话筒<br/>
<@a href="/p?n_lw/${pid}/o/${wishId}/"/>打开许愿瓶</a><br/>
</#if>
<#if fish?exists>
<@a href="/p?n_lw/${pid}/f/"/>继续钓鱼</a><br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>