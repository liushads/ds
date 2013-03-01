<#include "/include/header.ftl">
<card title="${game_title}"><p>
任务分类:<br/>
<a href="/active.wml">活动公告</a><br/>
<#--<a href="/new_update.wml">最近更新</a><br/>-->
<#--<a href="/new_reply.wml">建议回复</a><br/>-->
<@a href="/p?m/${pid}/list/1"/>主线任务(${main})</a><br/>
<@a href="/p?m/${pid}/list/2"/>支线任务(${playerType})</a><br/>
<@a href="/p?m/${pid}/list/3"/>日常任务(${yabiao})</a><br/>
<@a href="/p?m/${pid}/list/4"/>副本任务(${daily})</a><br/>
<@a href="/p?m_N/${pid}/"/>可接任务(主)</a><br/>
<@a href="/p?m_N/${pid}/0/"/>可接任务(支)</a><br/>
<@gogame />
【温馨提示】每天上线请到月牙村[新手村]福利坊找福利官领取福利包可以做其他任务<br/>
</p>
</card>
</wml>
