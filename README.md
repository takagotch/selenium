### selenium / webdriver
---
.java
http://www.seleniumhq.org/docs/

.py
https://pypi.org/project/selenium/

```java

```

```sh
java -jar selenium-server-standalone-3.141.0.jar
```

```py
from selenium import webdriver

browser = webdriver.Firefox()
browser.get('http://seleniumhq.org/')



from selenium import webdriver
from selenium.webdriver.common.keys import Keys

browser = webdriver.Firefox()

browser.get('http://www.yahoo.com')
assert 'Yahoo' in browser.title

elem = browser.find_element_by_name('p')
elem.send_keys('seleniumhq' + Keys.RETURN)

browser.quit()


import unittest
from selenium import webdriver

class GoogleTestCase(unittest.TestCase):
  
  def setUp(self):
    self.browser = webdriver.Firefox()
    self.addCleanup(self.browser.quit)
    
  def testPageTitle(self):
    self.browser.get('http://www.google.com')
    self.assertIn('Google', self.browser.title)

if __name__ == '__main__':
  unittest.main(verbosity=2)

```

