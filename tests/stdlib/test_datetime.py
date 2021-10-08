from ..utils import TranspileTestCase


class DateTimeModuleTests(TranspileTestCase):

    @staticmethod
    def create_date_test(command):
        return f"""
            import datetime
            try:
                date = datetime.date(year=2021, month=10, day=1)
                {command}
            except Exception as e:
                print(type(e), ':', e)
        """

    def test_read_year(self):
        self.assertCodeExecution(self.create_date_test("print(date.year)"))

    def test_write_year(self):
        self.assertCodeExecution(self.create_date_test("date.year = 2016"))

    def test_setattr_invalid(self):
        self.assertCodeExecution(self.create_date_test("date.__setattr__(42, 15)"))

    def test_setattr_nonexisting(self):
        self.assertCodeExecution(self.create_date_test("date.__setattr__('foo', 42)"))

    def test_setattr_existing(self):
        self.assertCodeExecution(self.create_date_test("date.__setattr__('year', 42)"))

    def test_read_month(self):
        self.assertCodeExecution(self.create_date_test("print(date.month)"))

    def test_write_month(self):
        self.assertCodeExecution(self.create_date_test("date.month = 6"))

    def test_read_day(self):
        self.assertCodeExecution(self.create_date_test("print(date.day)"))

    def test_write_day(self):
        self.assertCodeExecution(self.create_date_test("date.day = 20"))
