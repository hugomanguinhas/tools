 Copyright 2010 EDL FOUNDATION

 Licensed under the EUPL, Version 1.1 or as soon they
 will be approved by the European Commission - subsequent
 versions of the EUPL (the "Licence");
 you may not use this work except in compliance with the
 Licence.
 You may obtain a copy of the Licence at:

 http://ec.europa.eu/idabc/eupl

 Unless required by applicable law or agreed to in
 writing, software distributed under the Licence is
 distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 express or implied.
 See the Licence for the specific language governing
 permissions and limitations under the Licence.


 Created by: Jacob Lundqvist (Jacob.Lundqvist@gmail.com)

 Initial release: 2010-02-05
 Version 1.1 2010-06-09

===============================================================================

The purpose of this packet is to administer the translations of europeana.eu

Currently we keep the site in 28 languages, and this has made the translation
process rather complicated.

The workflow with this tool is that the webmaster works with html templates
and then just tags his english texts as translation keys. Then translators are
notified. When a language is completed the webmaster is notified, when all
changes are translated the new pages are copied to the production web server.

To avoid spamming a given translation team (one or more people) can only be in
two states "translations pending" and "all done". Only when a template change moves a language out of the "all done" state,
translators for that language are notified of pending translations.

This tool also handles the properties files - that is separate translations used
internally in the europeana.eu server. From a translator perspective there is
no difference if the translation key comes from a template or from the properties file.

The keys for the translations are in one of two formats:

If its a phrase or longer block of text, the text itself is the key, this is the normal
case and saves us time in that the english version of the text will automatically be
the default if a given key isn't translated.

In some cases, especially with short one-word strings, a context need to be given, then
we use this syntax (completely random, feel free to use what suits you)

#The[about_us: paragraph More about, last list item - prefix for enews.php]

witch in the english case would be translated to "The"

For translations we use django-rosetta (http://code.google.com/p/django-rosetta/)
So translators should work in the /rosetta hierarchy, but normally they would get
a mail notification on pending translates, and given the correct url in the notification,
so shouldn't need to care about the url to use.

This tool is intended to be run from an apache server, using the WSGI wrapper.

Dependencies

0. GetText   apt-get install gettext
1. A resent django - 1.3 or higer should work (tested with 1.3.1)
2. django-rosetta - we use 0.6.5 for the moment (2012-02-09) (included in this code-tree)


====================   Rosetta patches aplied   ===========================

---------------   rosetta/templates/rosetta/base.html   -----------------

            {% block header %}
            <div id="branding">
                <h1 id="site-name">
		<a href="{% url rosetta-pick-file %}">Rosetta select language</a>
		&nbsp;-&nbsp;
		<a href="/">Static pages</a>
		</h1>
            </div>
            {% endblock %}

-------------------------   rosetta/views.py   --------------------------

(in top of file after other includes)
from gen_utils.rosetta_extras import translator_allowed

(in list_languages() arround #303)
    for language in settings.LANGUAGES:
        # Patch to only allow translator to handle assigned language
        if not translator_allowed(request.user, language[0]):
            continue

-------------------------   removal of fuzzy and obsolete ----------
added a file rosetta_debug.py to the rosetta codebase

search for use_fuzzy in rosetas .py and .html files to see all references

site-packages/django/core/management/commands/makemessages.py
arround line 262, add the python-format filter:
                    msgs = msgs.replace('\n#, python-format','')
                    if os.path.exists(potfile):
                        # Strip the header
                        msgs = '\n'.join(dropwhile(len, msgs.split('\n')))



=============== Some hints on multilingual setup ===================

To get languages to work in django 1.2 or greater, in settings.py:

MIDDLEWARE_CLASSES = (
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.locale.LocaleMiddleware',
    'django.middleware.common.CommonMiddleware',
    ...
    )

--------------------------------------
# Sample language setter

from django.http import HttpResponseRedirect

LANG_KEY = 'django_language'

def set_lang(request, lang, next_page='/'):
    if lang == 'sv':
        request.session[LANG_KEY] = 'sv-se'
    elif lang in ('en','de'):
        request.session[LANG_KEY] = lang
    #else:
        # If youre ambitious, inform user of bad lang selection...
    return HttpResponseRedirect(next_page)
---------------------------------

Item count - currently manual, should be automatic shortly, for the
moment edit theese two files, and look for europeana_item_count_mill
apps/multi_lingo/views.py
apps/multi_lingo/utils.py



==========   new change in how imgs should be refered  2010-11-09   ==========
images / sp => sp / img  {extra space to not be cought when searching for this}



rosetta/views.py:can_translate()
