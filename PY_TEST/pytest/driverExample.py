from selenium import webdriver
from selenium.webdriver.common.keys import Keys

user = "ddesai2@gmail.com"
pwd = "humtum"
driver = webdriver.Chrome('C:/Users/RRDD/Desktop/Automation/Python/Workspace/PY_TEST/drivers/chromedriver.exe')
driver.get("http://www.facebook.com")
assert "Facebook" in driver.title
elem = driver.find_element_by_id("email")
elem.send_keys(user)
elem = driver.find_element_by_id("pass")
elem.send_keys(pwd)
elem.send_keys(Keys.RETURN)
#driver.close()