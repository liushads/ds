<#include "/include/header.ftl">
<card title="${game_title}"><p>
情人节活动送祝福<br/>
祝福列表：<br/>
祝福<#if otherPlayer?exists>${otherPlayer.name}</#if>的许愿<br/>
请选择你的祝福<br/>
<@a href="/p?n_love/${pid}/send/${otherId}/1/"/>祝你愿望成真 (2金)</a><br/>
<@a href="/p?n_love/${pid}/send/${otherId}/2/"/>祝你温馨浪漫(1金)</a><br/>
<@a href="/p?n_love/${pid}/send/${otherId}/3/"/>祝你事业顺利(1000银)</a><br/>
<@a href="/p?n_love/${pid}/send/${otherId}/4/"/>祝你爱情顺利(500银)</a><br/>
<@a href="/p?n_love/${pid}/send/${otherId}/5/"/>祝你家庭和睦(100银)</a><br/>
<@a href="/p?m_I/${pid}/${otherId}/"/>发私聊祝福</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>