<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村赌场<br/>
三个骰子下注<br/>
<#if mtype?exists>
你已押注
<#if mtype == '1'>
【豹子】,赔率是1赔200,
</#if>
<#if mtype == '2'>
【顺子】,赔率是是1赔10,
</#if>
<#if mtype == '3'>
【大】,赔率是是1赔2,
</#if>
<#if mtype == '4'>
【小】,赔率是是1赔2,
</#if>
系统将扣除了1银的赌资<br/>
</#if>
【温馨提示】：赌博有害，切勿沉迷<br/>
<@a href="/p?n_Gx/${pid}/ch/th/c/${mtype}"/>确定</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>